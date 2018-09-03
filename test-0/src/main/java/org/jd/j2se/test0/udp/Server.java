package org.jd.j2se.test0.udp;

import org.jd.util.Logger;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by cuijiandong on 2018/2/22.
 */
public class Server {

    public static void main(String []s) throws Exception {

        DatagramChannel c = DatagramChannel.open();
        c.bind(new InetSocketAddress("localhost",1000));
        ByteBuffer b=ByteBuffer.allocate(32);
        while(true){
            SocketAddress rec = c.receive(b);
            b.flip();
//            int i=b.getInt();
            Logger.l.log("收到数据 ",new String(b.array())," 来自:",rec);

//            b.clear();
//            b.putInt(++i);
//            b.flip();
//            c.send(b,rec);
//            b.clear();
//            Logger.l.log("发送数据 ",i," 到",rec);
        }
    }
}
