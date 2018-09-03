package org.jd.proxy.http;

import org.jd.proxy.nio.Pump;
import org.jd.proxy.util.ArrayUtil;
import org.jd.proxy.util.IOUtil;
import org.jd.proxy.util.StringUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class ProxyServer extends Proxy {

    private ByteBuffer buffer = ByteBuffer.allocate(8192);

    @Override
    public void run() {
        String firstLine = null;
        try {
            byte[] array = buffer.array();
            int rnIndex;
            do {
                buffer.mark();
                if (accept.read(buffer) == -1) {
                    accept.close();
                    buffer.flip();
                    log.error("协议异常,关闭管道！ {} ",new String(array, 0, buffer.limit()));
                    return;
                }
                buffer.limit(buffer.position()).reset();//本次读到的数据
                enDecryptor.decryptor.apply(buffer);//解密
                if ((rnIndex = ArrayUtil.indexOf(array, (byte) '\n', buffer.position(), buffer.limit())) > 0) {
                    buffer.position(0);//设置buffer为读状态
                    break;
                }
                buffer.position(buffer.limit()).limit(buffer.capacity());//恢复buffer为写状态
            } while (true);

            firstLine = new String(array, 0, rnIndex - 1);
            log.info(firstLine);
            try{
                if (firstLine.startsWith("CONNECT ")) {
                    httpsProxy(firstLine);
                } else {
                    httpProxy(firstLine);
                }
            }catch (IOException e0){
                accept.close();
                log.error("ProxyServer IO异常结束1 {}",e0.getMessage());
            }
        } catch (Exception e) {
            log.error("ProxyServer 异常结束2: {} | {}", e.getMessage(), firstLine);
        }
    }

    private static final int httpLen = "http://".length();

    //处理第一行请求头 GET http://www.smts.com/ HTTP/1.1
    private void httpProxy(String firstLine) throws Exception {
        // firstLine: "GET http://www.a.com:8080/index.html HTTP/1.1"
        String[] cmd = StringUtil.splitAndGet(firstLine, " ", 0, 1);
        //cmd: ["GET", "http://www.a.com:8080/index.html", "HTTP/1.1"]
        int hpEnd = cmd[1].indexOf("/", httpLen);//第三条斜线的坐标
        String hostAndPort = cmd[1].substring(httpLen, hpEnd);
        // hostAndPort: "www.a.com:8080"
        buffer.position(httpLen + hostAndPort.length());
        //跳过 "http://www.a.com:8080".length()个字节， 正常请求为 GET /index.html HTTP/1.1
//        String s0 = new String(buffer.array(), buffer.position(), buffer.limit());
        buffer.mark();
        buffer.put((cmd[0] + " ").getBytes()).reset();
//        String s1 = new String(buffer.array(), buffer.position(), buffer.limit());
        SocketChannel server = connect(hostAndPort);
        IOUtil.write(server, buffer);
        channelCopy(server);
    }

    //CONNECT www.alipay.com:443 HTTP/1.1 ...
    private void httpsProxy(String firstLine) throws Exception {
        SocketChannel server = connect(StringUtil.splitAndGet(firstLine, " ", 1));
        buffer.clear();
        buffer.put("HTTP/1.1 200 Connection Established \r\n\r\n".getBytes());
        buffer.flip();
        enDecryptor.encryptor.apply(buffer);
        IOUtil.write(accept, buffer);
        channelCopy(server);
    }

    private SocketChannel connect(String hostAndPort) throws IOException {
        int i = hostAndPort.indexOf(":");
        if (i > 0)
            return SocketChannel.open(new InetSocketAddress(hostAndPort.substring(0, i), Integer.valueOf(hostAndPort.substring(i + 1))));
        return SocketChannel.open(new InetSocketAddress(hostAndPort, 80));
    }

    public ProxyServer(SocketChannel channel) {
        this.accept = channel;
    }

    void channelCopy(SocketChannel server) {
        new Pump(accept, server, enDecryptor.decryptor).start();
        new Pump(server, accept, enDecryptor.encryptor).start();
    }
}
