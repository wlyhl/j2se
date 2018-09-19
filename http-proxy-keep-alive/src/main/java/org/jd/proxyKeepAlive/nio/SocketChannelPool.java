package org.jd.proxyKeepAlive.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by cuijiandong on 2018/8/31.
 */
public class SocketChannelPool {
    private InetSocketAddress remote;
    private int max;
    Stack<SocketChannel> idle=new Stack<>();
    public SocketChannelPool(InetSocketAddress remote, int max) {
        this.remote = remote;
        this.max = max;
    }
    public SocketChannel getSocketChannel(){
        return null;
    }
}
