package org.jd.proxy.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;

public class IOUtil {
    /**
     * 关闭流
     *
     * @param ios
     */
    public static void close(Closeable... ios) {
        for (Closeable io : ios) {
            try {
                io.close();
            } catch (Exception e) {
//				e.printStackTrace();
            }
        }
    }

    /**
     * 将数据写入SocketChannel直到写完
     */
    public static void write(SocketChannel c, ByteBuffer b) throws Exception {
        while (b.hasRemaining()) {
            switch (c.write(b)) {
                case 0:
                    Thread.sleep(10);
                    break;
                case -1:
                    throw new IOException("输出流已关闭");
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

    public static void readFully(ByteChannel c, ByteBuffer dst) throws IOException {
        while (dst.hasRemaining() && c.read(dst) >= 0) {
            sleep(1);
        }
    }

    public static void writeFully(ByteChannel c, ByteBuffer dst) throws IOException {
        while (dst.hasRemaining() && c.write(dst) >= 0) {
            sleep(1);
        }
    }
}
