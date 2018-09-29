package org.jd.proxyKeepAlive.client;

import org.jd.proxyKeepAlive.pool.Pool;
import org.jd.proxyKeepAlive.pool.net.SocketChannelFactory;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 * Created by cuijiandong on 2018/9/27.
 */
public class ClientConsumer implements Consumer<SocketChannel> {

    private final BlockingQueue<SocketChannel> serverConnectedChannel = new ArrayBlockingQueue<>(20);
    private final Pool<SocketChannel> pool;

    public ClientConsumer(String serverHost, int port) {
        this.pool = new Pool<>(new SocketChannelFactory(new InetSocketAddress(serverHost, port)));
    }

    @Override
    public void accept(final SocketChannel accept) {
        new Thread(new ClientJob(accept, pool.get())).start();
    }
}