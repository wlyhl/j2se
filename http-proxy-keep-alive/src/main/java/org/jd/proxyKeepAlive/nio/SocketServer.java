package org.jd.proxyKeepAlive.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

/**
 * Created by cuijiandong on 2018/9/28.
 */
public class SocketServer implements Runnable {
    private Logger log = LoggerFactory.getLogger(SocketServer.class);
    private int port;
    private Consumer<SocketChannel> consumer;

    public SocketServer(int port, Consumer<SocketChannel> consumer) {
        this.port = port;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(true);
            server.bind(new InetSocketAddress(port));
            int c = 0;
            log.info("启动监听 {} ", port);
            while (server.isOpen()) {
                SocketChannel accept = server.accept();
                log.info("第 {} 个连接", c++, accept);
                accept.setOption(StandardSocketOptions.TCP_NODELAY, true);
                consumer.accept(accept);
                ThreadGroup t = Thread.currentThread().getThreadGroup();
                log.info("当前线程 active {} / {} ", t.activeCount(), t.activeGroupCount());
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
