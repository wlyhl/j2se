package org.jd.proxyKeepAlive.server;

import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

/**
 * Created by cuijiandong on 2018/9/28.
 */
public class ServerConsumer implements Consumer<SocketChannel> {


    @Override
    public void accept(SocketChannel channel) {
        new Thread(new ServerJob(channel)).start();
    }

}
