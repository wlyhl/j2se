package org.jd.port.scanner;

import org.jd.util.ExceptionUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by cuijiandong on 2018/1/5.
 */
public class Scanner {
    LinkedBlockingQueue<Address> queue;
    ArrayList<Thread> threads=new ArrayList<>();
    public static int timeOut;
    /**
     * 扫描器，开多个线程开始扫描
     *
     * @param maxThread      开多少个线程扫描
     * @param connectSuccess 连接成功的回调
     * @param connectFailed  连接失败的回调
     */
    public Scanner(int maxThread, Address.CallBack connectSuccess, Address.CallBack connectFailed) {
        queue = new LinkedBlockingQueue<>(1);
        while (maxThread-- > 0) {
            Thread job = new ScanJob(connectSuccess,connectFailed,queue);
            threads.add(job);
            job.start();
        }
    }


    public void scan(String host, int port) {
        try {
            queue.put(new Address(host, port));
        } catch (InterruptedException e) {
            ExceptionUtil.thro(e);
        }
    }
    public void interruptAllJob(){
        for(int i=threads.size()-1;i>=0;i--){//倒着remove，减少不必要的数组复制
            threads.get(i).interrupt();
            threads.remove(i);
        }
    }
}
