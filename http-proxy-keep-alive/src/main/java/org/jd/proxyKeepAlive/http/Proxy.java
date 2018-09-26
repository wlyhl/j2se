package org.jd.proxyKeepAlive.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by cuijiandong on 2018/8/31.
 */
public abstract class Proxy extends Thread {
    Logger log = LoggerFactory.getLogger(getClass());
    SocketChannel accept;
    SocketChannel server;
    static EnDecryptor enDecryptor;

    /**
     * server: 监听 2001 端口，解密密码 abc
     * 2001 abc
     * client: 监听 2000 端口，解密密码 abc ，远程服务器地址 127.0.0.1:2001
     * 2000 abc 127.0.0.1 2001
     */
    public void start(String[] a) {
        enDecryptor = new XOREnDecryptor(a[1].getBytes());
        try {
            ServerSocketChannel proxy = ServerSocketChannel.open();
            proxy.configureBlocking(true);
            proxy.bind(new InetSocketAddress(Integer.valueOf(a[0])));
            int c = 1;
            log.info("{}已启动,监听端口 {} ", getClass().getSimpleName(), a[0]);
            while (true) {
                SocketChannel channel = proxy.accept();
                channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
                log.info("第 {} 个短连接", c++, channel);
                getInstance(channel,a).start();

                ThreadGroup t = Thread.currentThread().getThreadGroup();
                log.info("当前线程 active {} / {} ", t.activeCount(), t.activeGroupCount());
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    abstract Proxy getInstance(SocketChannel accept, String[] arg);
}
