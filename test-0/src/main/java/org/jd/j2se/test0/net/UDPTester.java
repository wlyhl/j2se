package org.jd.j2se.test0.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Created by cuijiandong on 2018/8/31.
 */
public class UDPTester {
    static InetSocketAddress ip;
    static int size = 1024 * 1024 * 500;

    public static void start(InetSocketAddress sip) {
        ip = sip;
        serverStart();
        clientStart();
    }

    private static void serverStart() {
        new Thread(() -> {
            try {
                ByteBuffer b = ByteBuffer.allocate(65507);
                DatagramChannel s = DatagramChannel.open().bind(ip);
                SocketAddress addr = s.receive(b);

                s.connect(addr);
                b.flip();
//                System.out.println("s_read " + b.remaining() + " index " + b.getInt());
//                b.position(0);
//                s.write(b);
                int i=0;
                while (s.isConnected()) {
                    b.clear();
                    int read = s.read(b);
                    b.flip();
                    int index = b.getInt();
                    i++;
                    if(i!=index){
                        System.out.println("lost "+i+" rec "+index);
                        i=index;
                    }
//                    System.out.println("s_read " + read + " index " +index);
                    b.position(0);
//                    s.write(b);
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

                DatagramChannel c = DatagramChannel.open();
                c.configureBlocking(true);

                c.connect(ip);
                new Thread(() -> {
                    int recSize = 0;
                    try {
                        ByteBuffer rb = ByteBuffer.allocate(65535);
                        while (c.isConnected()) {
                            rb.clear();
                            int read = c.read(rb);

                            rb.flip();
                            recSize += rb.remaining();
                            int anInt = rb.getInt();
//                            System.out.println("c_read " + read + " index " + anInt + "recSize" + recSize);
                            while (rb.hasRemaining()) {
                                if (rb.get() != 6) {
                                    c.close();
                                    throw new RuntimeException("数据错误！！！！！");
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                System.out.println(new Date().getTime() - start + "连接耗时");
                byte[] by = new byte[60000];
                for (int i = 0; i < by.length; i++) {
                    by[i] = 6;
                }
                ByteBuffer b = ByteBuffer.allocate(by.length + 10);
                System.out.println(new Date().getTime() - start + "分配耗时");
                int iMax = size / by.length;
                for (int i = 0; i < 100; i++) {
                    b.clear();
                    b.putInt(i);//第几个包
                    b.put(by);
                    b.flip();
                    c.write(b);
                }

                System.out.println(new Date().getTime() - start + "耗时");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
