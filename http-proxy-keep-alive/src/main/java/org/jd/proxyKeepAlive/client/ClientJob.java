package org.jd.proxyKeepAlive.client;

import org.jd.proxyKeepAlive.nio.DoublePump;
import org.jd.proxyKeepAlive.pool.Pooled;
import org.jd.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.channels.SocketChannel;

/**
 * Created by cuijiandong on 2018/9/27.
 */
public class ClientJob implements Runnable {
    private final SocketChannel accept;
    private final Pooled<SocketChannel> server;
    Logger log = LoggerFactory.getLogger(ClientJob.class);

    public ClientJob(SocketChannel accept, Pooled<SocketChannel> server) {
        this.accept = accept;
        this.server = server;
    }

    @Override
    public void run() {
        new DoublePump(accept, server.instance).onS1Read((bf, p) -> {
            bf.base.flip();
            try {
                log.info(new String(bf.base.array(),0,bf.base.limit(),"utf8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }).run();
        IOUtil.close(accept);
        if (server.instance.isOpen())
            server.takeBack();
    }
}