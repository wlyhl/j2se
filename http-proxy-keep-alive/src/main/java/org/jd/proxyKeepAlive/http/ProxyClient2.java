package org.jd.proxyKeepAlive.http;


import org.jd.proxyKeepAlive.nio.Pump;
import org.jd.proxyKeepAlive.pool.Factory;
import org.jd.proxyKeepAlive.pool.Pool;
import org.jd.proxyKeepAlive.pool.Pooled;
import org.jd.util.IOUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class ProxyClient2 {
    public static void main(String[] a) {

    }


}

class ProxyClientConsumer implements Consumer<SocketChannel> {

    private final BlockingQueue<SocketChannel> serverConnectedChannel = new ArrayBlockingQueue<>(20);
    private final Pool<SocketChannel> pool;

    public ProxyClientConsumer(String serverHost, int port) {
        this.pool = new Pool<>(new SocketChannelFactory(new InetSocketAddress(serverHost, port)));
    }

    @Override

    public void accept(final SocketChannel accept) {

    }
}

class ProxyClientJob extends Thread {
    private final SocketChannel accept;
    private final Pooled<SocketChannel> server;

    public ProxyClientJob(SocketChannel accept, Pooled<SocketChannel> server) {
        this.accept = accept;
        this.server = server;
    }

    @Override
    public void run() {
        Consumer end = (o) -> {
            IOUtil.close(accept);
            server.takeBack();
        };
        new Pump(accept, server.instance).setEnd(end).start();
        new Pump(server.instance, accept).setEnd(end).run();//当前线程执行
    }
}


class SocketChannelFactory implements Factory<Pooled<SocketChannel>> {
    private final InetSocketAddress address;

    public SocketChannelFactory(InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public Pooled<SocketChannel> newInstance() {
        try {
            SocketChannel channel = SocketChannel.open(address);
            channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
            channel.configureBlocking(true);
            return new Pooled<>(channel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}