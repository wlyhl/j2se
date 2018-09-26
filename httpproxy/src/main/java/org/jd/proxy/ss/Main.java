package org.jd.proxy.ss;

import org.jd.proxy.http.Proxy;
import org.jd.proxy.http.ProxyClient;
import org.jd.proxy.http.ProxyServer;
import org.jd.proxy.http.XOREnDecryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Main {
    static Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * 2000 127.0.0.1 2001 r
     * 2001 r
     */
    public static void main(String[] a) throws Exception {
        Proxy.enDecryptor = (a.length == 4 || a.length == 2) ? new XOREnDecryptor(a[a.length - 1].getBytes()) : new XOREnDecryptor(new byte[0]);

        try {
            ServerSocketChannel proxy = ServerSocketChannel.open();
            proxy.configureBlocking(true);
            proxy.bind(new InetSocketAddress(Integer.valueOf(a[0])));
            int c = 0;
            log.info("已启动。端口 {} ", a[0]);
            while (true) {
                SocketChannel channel = proxy.accept();
                channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
                log.info("第 {} 个连接", c++, channel);
                // 2000 127.0.0.1 2001
                if (a.length == 4)
                    new ProxyClient(channel, a[1], Integer.valueOf(a[2])).start();
                if (a.length == 2)
                    new ProxyServer(channel).start();

                ThreadGroup t = Thread.currentThread().getThreadGroup();
                log.info("当前线程 active {} / {} ", t.activeCount(), t.activeGroupCount());
            }
        } catch (Exception e) {
            log.error("", e);
        }


    }

}
