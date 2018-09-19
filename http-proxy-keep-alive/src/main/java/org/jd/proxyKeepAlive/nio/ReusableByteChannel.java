package org.jd.proxyKeepAlive.nio;

import org.jd.util.IOUtil;

import java.io.IOException;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

/**
 * 可复用的链接
 * Created by cuijiandong on 2018/9/3.
 */
public class ReusableByteChannel implements ByteChannel {
    public final DirectiveListener[] listeners = new DirectiveListener[128];
    private final ByteChannel c;
    private static final byte NULL = -127;
    /**
     * 表示后面跟的是数据，data表示数据长度
     */
    private static final byte DATA = -126;
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
    private final ByteBuffer readBuffer = ByteBuffer.allocate(5);//一字节指令，四字节数据

    public synchronized int read(ByteBuffer dst) throws IOException {
        synchronized (readBuffer) {
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
                case DATA:
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
//                    if (directive >= 0 && listeners[directive] != null)
                    listeners[directive].doSth(data);
                    directive = NULL;
            }
            return 0;
        }
    }

    private final ByteBuffer writeBuffer = ByteBuffer.allocate(5);//一字节指令，四字节数据

    public int write(ByteBuffer src) throws IOException {
        synchronized (writeBuffer) {
            int remaining = src.remaining();
            write(DATA, remaining);
            IOUtil.writeFully(c, src);
            return remaining;
        }
    }

    public void write(byte directive, int data) throws IOException {
        synchronized (writeBuffer) {
            writeBuffer.clear();
            writeBuffer.put(directive).putInt(data).flip();
            IOUtil.writeFully(c, writeBuffer);
        }
    }

    @Override
    public boolean isOpen() {
        return c.isOpen();
    }

    public void close() {
        IOUtil.close(c);
    }
}
