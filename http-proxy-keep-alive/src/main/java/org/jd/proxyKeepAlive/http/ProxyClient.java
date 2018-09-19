package org.jd.proxyKeepAlive.http;


import org.jd.proxyKeepAlive.nio.Pump;
import org.jd.util.IOUtil;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class ProxyClient extends Proxy {
    static volatile int c = 1;//计数
    private static final ConcurrentLinkedQueue<ProxyClient> queue = new ConcurrentLinkedQueue<>();
    private Consumer end;

    public ProxyClient() {
    }

    private ProxyClient(SocketChannel channel, String serverHost, int serverPort) {
        log.info("新建第 {} 个长连接", c++);
        this.accept = channel;
        try {
            server = SocketChannel.open(new InetSocketAddress(serverHost, serverPort));
            server.setOption(StandardSocketOptions.TCP_NODELAY, true);
            server.configureBlocking(true);
            end = (n) -> {//管道复制结束时，关闭来自浏览器的连接，保持连接到远程代理的连接，并放入队列等待下次取用
                IOUtil.close(accept);
                accept = null;
                synchronized (queue) {
                    if (!queue.contains(this)) {
                        queue.add(this);
                        log.info("连接空闲");
                    }

                }
            };
        } catch (Exception e) {
            log.error("连接{}:{}失败", serverHost, serverPort);
            log.error("", e);
            IOUtil.close(accept, server);
            server = null;
        }
    }

    @Override
    public synchronized void run() {
        try {
            while (true) {
                if (server == null)
                    return;
                if (accept == null) {
                    wait(1000 * 60);
                    continue;
                }
                new Pump(accept, server).setProcessor(enDecryptor.encryptor).setEnd(end).start();
                new Pump(server, accept).setProcessor(enDecryptor.decryptor).setEnd(end).run();//当前线程执行
            }
        } catch (Exception e) {
            e.printStackTrace();
            IOUtil.close(server);
        }
    }

    @Override
    public synchronized void start() {
        if (State.NEW == getState())
            super.start();
    }

    @Override
    Proxy getInstance(SocketChannel accept, String[] arg) {
        ProxyClient i;
        while ((i = queue.poll()) != null && !i.isAlive()) ;
        log.info("剩余空闲连接数{}", queue.size());
        if (i != null) {
            synchronized (i) {
                i.accept = accept;
                i.notify();
                return i;
            }
        } else return new ProxyClient(accept, arg[2], Integer.valueOf(arg[3]));
    }
}
