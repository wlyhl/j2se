package org.jd.j2se.test0.udp;

import com.sun.deploy.pings.Pings;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by cuijiandong on 2018/2/22.
 */
public class C2 {
    public static void main(String []ss) throws Exception {
        DatagramSocket s=new DatagramSocket(new InetSocketAddress(1000));
        byte[] str="hello".getBytes();
        InetAddress.getLocalHost();
        s.send(new DatagramPacket(str,str.length,new InetSocketAddress("123.234.83.98",1001)));
    }
}
