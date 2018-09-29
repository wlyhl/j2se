package org.jd.proxyKeepAlive.server;

import org.jd.proxyKeepAlive.nio.DoublePump;
import org.jd.util.IOUtil;
import org.jd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by cuijiandong on 2018/9/27.
 */
public class ServerJob implements Runnable {
    Logger log = LoggerFactory.getLogger(ServerJob.class);
    private final SocketChannel accept;
    ByteBuffer buffer = ByteBuffer.allocate(8192);

    public ServerJob(SocketChannel accept) {
        this.accept = accept;
    }

    public void run() {
        String firstLine = null;
        buffer.clear();
        SocketChannel server = null;
        try {
            ByteBuffer smallBuffer = ByteBuffer.allocate(256);
            do {
                smallBuffer.clear();
                IOUtil.readAtLeast1(accept, smallBuffer);
                smallBuffer.flip();
                while (smallBuffer.hasRemaining()) {
                    byte b = smallBuffer.get();
                    if (b == '\n' && firstLine == null)
                        firstLine = new String(buffer.array(), 0, buffer.position());
                    buffer.put(b);
                }
            } while (firstLine == null);
            log.info("读到第一行数据---{}", firstLine);
            //连接服务器
            server = firstLine.startsWith("CONNECT ") ? connect(firstLine) : http(firstLine);
            //复制数据
            new DoublePump(accept, server).run();
        } catch (Exception e) {
            log.error("异常结束", e);
        }
        IOUtil.close(server);
    }

    private static final int httpLen = "http://".length();

    /**
     * 处理第一行请求头 GET http://www.smts.com/index HTTP/1.1
     * GET /index HTTP/1.1
     */
    private SocketChannel http(String firstLine) throws Exception {
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
        SocketChannel server = connectTo(hostAndPort);
        IOUtil.writeFully(server, buffer);
        return server;
    }

    //CONNECT www.alipay.com:443 HTTP/1.1 ...
    private SocketChannel connect(String firstLine) throws Exception {
        SocketChannel server = connectTo(StringUtil.splitAndGet(firstLine, " ", 1));
        buffer.clear();
        buffer.put("HTTP/1.1 200 Connection Established \r\n\r\n".getBytes());
        buffer.flip();
//        enDecryptor.encryptor.apply(buffer);
        IOUtil.writeFully(accept, buffer);
        return server;
    }

    private SocketChannel connectTo(String hostAndPort) throws IOException {
        int i = hostAndPort.indexOf(":");
        if (i > 0)
            return SocketChannel.open(new InetSocketAddress(hostAndPort.substring(0, i), Integer.valueOf(hostAndPort.substring(i + 1))));
        return SocketChannel.open(new InetSocketAddress(hostAndPort, 80));
    }

}