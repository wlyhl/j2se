package org.jd.j2se.test0.net;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by cuijiandong on 2018/8/29.
 */
public class SpeedTester {
    public static void main(String[] a) throws Exception {
//        InetSocketAddress ip = new InetSocketAddress("127.0.0.1", 1000);
////        UDPTester.start(ip);
//        TCPTester.start(ip);

//        System.out.println(InetAddress.getLocalHost());

        Enumeration<NetworkInterface> ns = NetworkInterface.getNetworkInterfaces();
        while (ns.hasMoreElements()) {
            NetworkInterface n = ns.nextElement();
            if(n.isUp())
            System.out.println(n.getName()+"---"+n.isUp()+"---"+n.isVirtual());
            Enumeration<InetAddress> i = n.getInetAddresses();
            while (i.hasMoreElements())
                System.out.println(i.nextElement());
        }

    }

}
