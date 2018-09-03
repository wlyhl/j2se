package org.jd.proxy.nio;

import org.jd.proxy.util.IOUtil;

import java.io.IOException;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

/**
 * 可复用的链接
 * Created by cuijiandong on 2018/9/3.
 */
public class ReusableByteChannel {
    public final DirectiveListener[] listeners = new DirectiveListener[128];

    private final ByteChannel c;
    private static final byte NULL = -127;
    /**
     * 读数据
     */
    private static final byte READ = -126;
    /**
     * 本次会话结束
     */
    private static final byte SESSION_END = 0;

    public ReusableByteChannel(ByteChannel c) {
        this.c = c;
    }

    private int read = 0;//已读数据
    private byte directive = NULL;//读到的指令
    private int data;//读到指令的数据
    private ByteBuffer readBuffer = ByteBuffer.allocate(5);//一字节指令，四字节数据

    public synchronized int read(ByteBuffer dst) throws IOException {
        if (directive == NULL) {//读取指令
            readBuffer.clear();
            IOUtil.readFully(c, readBuffer);
            if (readBuffer.hasRemaining()) {
                close();
                throw new ProtocolException("指令不完整");
            }
            readBuffer.flip();
            directive = readBuffer.get();
            data = readBuffer.getInt();
        }
        switch (directive) {
            case NULL://空指令
                return 0;
            case READ:
                int i = dst.position() + (data - read);
                if (i < dst.limit())
                    dst.limit(i);//还需读取 data-read 字节，不能多读
                i = c.read(dst);
                read += i;//累计读
                if (read == data) {//读完一次，重置
                    read = 0;
                    directive = NULL;
                } else if (read > data) {
                    throw new ProtocolException("读取数据过多！");
                }
                return i;
            default:
                if (directive >= 0 && listeners[directive] != null)
                    listeners[directive].doSth(data);
                directive = NULL;
        }
        return 0;
    }

    public synchronized int write(ByteBuffer src) throws IOException {
        return c.write(src);
    }

    public void close() {
        IOUtil.close(c);
    }
}
