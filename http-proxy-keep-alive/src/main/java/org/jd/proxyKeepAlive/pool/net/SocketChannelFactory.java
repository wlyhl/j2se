package org.jd.proxyKeepAlive.pool.net;

import org.jd.proxyKeepAlive.pool.Factory;
import org.jd.proxyKeepAlive.pool.Pooled;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SocketChannel;

/**
 * Created by cuijiandong on 2018/9/27.
 */
public class SocketChannelFactory implements Factory<Pooled<SocketChannel>> {
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