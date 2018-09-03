package org.jd.proxy.http;


import org.jd.proxy.nio.Pump;
import org.jd.proxy.util.IOUtil;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SocketChannel;

public class ProxyClient extends Proxy {
    private SocketChannel server;

    @Override
    public void run() {
        if (server == null)
            return;
        try {
            channelCopy(server);
        } catch (Exception e) {
            e.printStackTrace();
            IOUtil.close(server);
        }
    }

    public ProxyClient(SocketChannel channel, String serverHost, int serverPort) {
        this.accept = channel;
        try {
            server = SocketChannel.open(new InetSocketAddress(serverHost, serverPort));
            server.setOption(StandardSocketOptions.TCP_NODELAY, true);
            server.configureBlocking(true);
        } catch (Exception e) {
            IOUtil.close(channel);
            server = null;
            log.error("连接{}:{}失败", serverHost, serverPort);
            log.error("",e);
        }
    }

    void channelCopy(SocketChannel server) {
        new Pump(accept, server, enDecryptor.encryptor).start();
        new Pump(server, accept, enDecryptor.decryptor).start();
    }
}
