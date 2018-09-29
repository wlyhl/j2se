package org.jd.proxyKeepAlive;

import org.jd.proxyKeepAlive.client.ClientConsumer;
import org.jd.proxyKeepAlive.nio.SocketServer;
import org.jd.proxyKeepAlive.server.ServerConsumer;

import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class Main {

    /**
     * server: 监听 2001 端口，解密密码 abc
     * 2001 abc
     * client: 监听 2000 端口，解密密码 abc ，远程服务器地址 127.0.0.1:2001
     * 2000 127.0.0.1 2001 abc
     */
    public static void main(String[] a) throws Exception {
        Consumer<SocketChannel> consumer = null;

        if (a.length > 2)// 2000 127.0.0.1 2001 abc
            consumer = new ClientConsumer(a[1], Integer.valueOf(a[2]));
        else if (a.length > 0 && a.length <= 2)//2001 abc
            consumer = new ServerConsumer();

        if (consumer != null)
            new SocketServer(Integer.valueOf(a[0]), consumer).run();
    }
}
