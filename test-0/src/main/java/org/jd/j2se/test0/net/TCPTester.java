package org.jd.j2se.test0.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.net.SocketOptions;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Created by cuijiandong on 2018/8/31.
 */
public class TCPTester {
    static InetSocketAddress ip;
    static int size=1024*1024*500;
    static int bufSize=1024*8;
    public static void start(InetSocketAddress sip) {
        ip=sip;
        serverStart();
        clientStart();
    }

    private static void serverStart() {

        new Thread(() -> {
            try {
                SocketChannel s = ServerSocketChannel.open().bind(ip).accept();
                s.setOption(StandardSocketOptions.TCP_NODELAY,true);
//                s.setOption(StandardSocketOptions.SO_RCVBUF,bufSize);
//                s.setOption(StandardSocketOptions.SO_SNDBUF,bufSize);

                ByteBuffer b = ByteBuffer.allocate(bufSize);
                while (s.isConnected()) {
                    b.clear();
                    s.read(b);
                    b.flip();
                    s.write(b);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void clientStart() {
        new Thread(() -> {
            try {
                long start = new Date().getTime();
                SocketChannel c = SocketChannel.open();
                c.setOption(StandardSocketOptions.TCP_NODELAY,true);
//                c.setOption(StandardSocketOptions.SO_RCVBUF,bufSize);
//                c.setOption(StandardSocketOptions.SO_SNDBUF,bufSize);

                c.connect(ip);
                new Thread(() -> {
                    int rSize=0;
                    try {

                        ByteBuffer rb = ByteBuffer.allocate(bufSize);
                        while (c.isConnected() && rSize<size) {
                            rb.clear();
                            int read = c.read(rb);
                            rSize+=read;
                            rb.flip();
                            while (rb.hasRemaining()) {
                                if (rb.get() != 6) {
                                    c.close();
                                    throw new RuntimeException("数据错误！！！！！");
                                }
                            }
                        }
                        System.out.println(new Date().getTime() - start + "接收耗时");
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(new Date().getTime() - start + "接收耗时，接收量"+rSize);
                    }
                }).start();
                System.out.println(new Date().getTime() - start + "连接耗时");
                byte[] by = new byte[bufSize];

                for (int i = 0; i < by.length; i++) {
                    by[i] = 6;
                }
                ByteBuffer b = ByteBuffer.allocate(by.length);
                System.out.println(new Date().getTime() - start + "分配耗时");
                int iMax=size/by.length;
                for (int i = 0; i < iMax; i++) {
                    b.clear();
                    b.put(by);
                    b.flip();
                    c.write(b);
                }
                System.out.println(new Date().getTime() - start + "耗时"+iMax);

//                c.close();
//                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
