package org.jd.port.scanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by cuijiandong on 2018/1/23.
 */
public class ScanJob extends Thread {
    Address.CallBack connectSuccess;
    Address.CallBack connectFailed;
    LinkedBlockingQueue<Address> queue;

    public ScanJob(Address.CallBack connectSuccess, Address.CallBack connectFailed, LinkedBlockingQueue<Address> queue) {
        this.connectSuccess = connectSuccess;
        this.connectFailed = connectFailed;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Address addr = queue.take();
                System.out.println("尝试连接" + addr);
                try (Socket s = new Socket()) {
                    s.connect(new InetSocketAddress(addr.host, addr.port), Scanner.timeOut);
                    if (s.isConnected())
                        connectSuccess.d(addr);
                } catch (IOException e) {
                    if (connectFailed != null)
                        connectFailed.d(addr);
                }
            }
            System.out.println(getName()+"线程终止");
        } catch (InterruptedException e) {
            System.out.println(getName()+"线程被中断");
        }
    }
}
