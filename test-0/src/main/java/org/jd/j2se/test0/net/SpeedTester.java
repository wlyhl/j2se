package org.jd.j2se.test0.net;

import java.net.InetSocketAddress;

/**
 * Created by cuijiandong on 2018/8/29.
 */
public class SpeedTester {
    public static void main(String[] a) throws Exception {
        InetSocketAddress ip = new InetSocketAddress("127.0.0.1", 1000);
//        UDPTester.start(ip);
        TCPTester.start(ip);
    }

}
