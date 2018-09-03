package org.jd.j2se.test0.udp.rudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * Created by cuijiandong on 2018/8/22.
 */
public class Rudp {
    public static void main(String a[]) throws IOException, InterruptedException {
       new Thread(()->{
           try {
               DatagramChannel d=DatagramChannel.open();
               d.bind(new InetSocketAddress("localhost",80));
               //不过鉴于Internet(非局域网)上的标准MTU值为576字节，所以建议在进行Internet的UDP编程时，最好将UDP的数据长度控制在548字节 (576-8-20)以内。
               ByteBuffer b = ByteBuffer.allocate(576);
               d.receive(b);

               b.flip();
               byte[] array = b.array();
               System.out.print(new String(array,Charset.forName("utf-8")));
           } catch (IOException e) {
               e.printStackTrace();
           }
       }).start();
       Thread.sleep(1000);
        DatagramChannel d=DatagramChannel.open();
        ByteBuffer b = ByteBuffer.allocate(576);
        b.put("asdasd6354".getBytes(Charset.forName("utf-8")));
        b.flip();
        d.send(b,new InetSocketAddress("localhost",80));
    }
}
