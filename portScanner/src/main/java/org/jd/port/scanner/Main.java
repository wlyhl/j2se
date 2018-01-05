package org.jd.port.scanner;

import java.io.IOException;

/**
 * Created by cuijiandong on 2018/1/5.
 */
public class Main {
    public static void main(String args[]) throws IOException {
        IpIterator.iterate("10.5.3.1","10.5.4.5",(ip)->new Scanner(ip,8081).start());
    }
}
