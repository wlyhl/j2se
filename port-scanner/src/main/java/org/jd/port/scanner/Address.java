package org.jd.port.scanner;

/**
 * Created by cuijiandong on 2018/1/23.
 */
public class Address {
    public String host;
    public int port;

    public Address(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public static interface CallBack{
        void d(Address addr);
    }

    @Override
    public String toString() {
        return host+":"+port;
    }
}
