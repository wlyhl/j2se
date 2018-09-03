package org.jd.j2se.test0.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by cuijiandong on 2018/2/22.
 */
public class UDProxy extends Thread {
    DatagramChannel from;
    SocketAddress to;
    private int bufferSize;
    private boolean reverseCopy;

    public UDProxy(int port, String remoteHost, int remotePort, int bufferSize) throws IOException {
        this(DatagramChannel.open(), new InetSocketAddress(remoteHost, remotePort), bufferSize, true);
        from.bind(new InetSocketAddress("localhost", port));
    }

    private UDProxy(DatagramChannel from, SocketAddress to, int bufferSize, boolean reverseCopy) {
        this.from = from;
        this.to = to;
        this.bufferSize = bufferSize;
        this.reverseCopy = reverseCopy;
    }

    @Override
    public void run() {
        try {
            ByteBuffer b = ByteBuffer.allocate(bufferSize);
            DatagramChannel proxy = DatagramChannel.open();
            if (reverseCopy)
                new UDProxy(proxy, copy(b, proxy), bufferSize, false).start();
            while (true)
                copy(b, proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SocketAddress copy(ByteBuffer b, DatagramChannel proxy) throws IOException {
        try {
            return from.receive(b);
        } finally {
            b.flip();
            proxy.send(b, to);
            b.clear();
        }
    }

}
