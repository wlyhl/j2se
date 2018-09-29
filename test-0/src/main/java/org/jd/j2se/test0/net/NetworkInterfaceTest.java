package org.jd.j2se.test0.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.channels.SocketChannel;

/**
 * Created by cuijiandong on 2018/9/28.
 */
public class NetworkInterfaceTest {
    public static void main(String[] aaa) throws IOException {

        NetworkInterface eth4 = NetworkInterface.getByName("eth4");
        InetAddress addr = eth4.getInetAddresses().nextElement();

        SocketChannel sc=SocketChannel.open();
//        sc.bind(new InetSocketAddress(addr,50));
        sc.connect(new InetSocketAddress("www.baidu.com",80));
        System.out.print(sc.isConnected());
    }

}
