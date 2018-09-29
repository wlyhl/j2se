package org.jd.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class IOUtil {
    /**
     * 关闭流
     *
     * @param ios
     */
    public static void close(Closeable... ios) {
        for (Closeable io : ios) {
            try {
                if (io != null)
                    io.close();
            } catch (Exception e) {
//				e.printStackTrace();
            }
        }
    }

    public static void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int readAtLeast1(ByteChannel c, ByteBuffer dst) throws IOException {
        int n;
        do {
            n = dealRW(c, c.read(dst));
        } while (n == 0);
        return n;
    }

    public static void readFully(ByteChannel c, ByteBuffer dst) throws IOException {
        while (dst.hasRemaining()) {
            readAtLeast1(c, dst);
        }
    }

    public static void writeFully(ByteChannel c, ByteBuffer... dst){
        try {
            for (ByteBuffer buffer : dst)
                while (buffer.hasRemaining()) {
                    dealRW(c, c.write(buffer));
                }
        }catch (IOException e){
            e.printStackTrace();
            close(c);
        }
    }

    /**
     * 读完或写完一次 如果读/写到的数据为0，sleep 如果为-1 关闭Channel并抛异常
     *
     * @param n 读到或写入的字节数
     * @return 读到的字节数
     */
    public static int dealRW(ByteChannel c, int n) throws IOException {
        if (n == -1) {
            close(c);
            throw new IOException("远端连接已关闭");
        }
        if (n == 0)
            sleep(50);
        return n;
    }
}
