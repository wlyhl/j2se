package org.jd.port.scanner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by cuijiandong on 2018/1/5.
 */
public class Scanner extends Thread{
    String host;
    int port;

    public Scanner(String host, int port) {
        this.host = host;
        this.port = port;
        System.out.println("test "+host+":"+port);
    }

    @Override
    public void run() {
        try(Socket s=new Socket()){
            s.connect(new InetSocketAddress(host,port),3000);
            if(s.isConnected())
                System.out.println(host+":"+port);
        }catch (Exception e){}

    }
}
