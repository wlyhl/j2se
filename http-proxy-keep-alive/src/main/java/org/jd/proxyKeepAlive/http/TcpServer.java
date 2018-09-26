package org.jd.proxyKeepAlive.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class TcpServer extends Thread {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final int port;
    private final Consumer<SocketChannel> consumer;

    public TcpServer(int port, Consumer<SocketChannel> consumer) {
        this.port = port;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(true);
            server.bind(new InetSocketAddress(port));
            log.info("{}已启动,监听端口 {} ", getClass().getSimpleName(), port);
            while (server.isOpen()) {
                SocketChannel accept = server.accept();
                accept.setOption(StandardSocketOptions.TCP_NODELAY, true);
                consumer.accept(accept);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
