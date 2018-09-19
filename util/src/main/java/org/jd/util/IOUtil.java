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
