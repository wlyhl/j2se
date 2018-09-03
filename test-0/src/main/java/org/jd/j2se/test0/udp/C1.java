package org.jd.j2se.test0.udp;

import org.jd.util.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;

/**
 * Created by cuijiandong on 2018/2/22.
 */
public class C1 {

    public static void main(String []s) throws Exception {
        InetSocketAddress server = new InetSocketAddress("localhost",2000);

        DatagramChannel c = DatagramChannel.open();
        c.bind(new InetSocketAddress(1088));
        ByteBuffer b=ByteBuffer.allocate(32);
        b.putInt(0);
        b.flip();
        c.send(b,server);
        while(true){
            b.clear();
            SocketAddress rec = c.receive(b);
            b.flip();
            int i=b.getInt();
            Logger.l.log(System.nanoTime(),"收到数据 ",i," 来自:",rec);
            Thread.sleep(2000);
            i++;
            Logger.l.log(System.nanoTime(),"发送数据 ",i);
            b.clear();
            b.putInt(i);
            b.flip();
            c.send(b,server);

        }
    }
}
