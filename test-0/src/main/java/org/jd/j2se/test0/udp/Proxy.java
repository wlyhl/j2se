package org.jd.j2se.test0.udp;

/**
 * Created by cuijiandong on 2018/2/22.
 */
public class Proxy {
    public static void main(String []s) throws Exception {
        new UDProxy(2000,"127.0.0.1",1000,1024).start();
    }
}
