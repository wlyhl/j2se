package org.jd.proxyKeepAlive.nio;

import org.jd.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.function.BiConsumer;

public class DoublePump extends Thread {
    private static Logger log = LoggerFactory.getLogger(DoublePump.class);
    private final SocketChannel s1, s2;
    private BiConsumer<BufferLink, DoublePump> s1Processor;
    private BiConsumer<BufferLink, DoublePump> s2Processor;
    private BiConsumer<SocketChannel, SocketChannel> end;

    public DoublePump(SocketChannel s1, SocketChannel s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public DoublePump onS1Read(BiConsumer<BufferLink, DoublePump> processor) {
        s1Processor = processor;
        return this;
    }

    public DoublePump onS2Read(BiConsumer<BufferLink, DoublePump> processor) {
        s2Processor = processor;
        return this;
    }

    /**
     * 在复制结束时的回调
     */
    public DoublePump setEnd(BiConsumer<SocketChannel, SocketChannel> end) {
        this.end = end;
        return this;
    }

    @Override
    public void run() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            BufferLink link = new BufferLink(buffer);
            int blockNum = 0;//连续未读到数据的次数
            do {
                int m = copy(s1, s2, link, s1Processor);
                int n = copy(s2, s1, link, s2Processor);
                if (m == 0 && n == 0)
                    IOUtil.sleep(++blockNum);
                else blockNum = 0;
            } while (blockNum < 200);//20秒钟无响应则关闭连接 1+2+....+199 毫秒
            log.info("管道复制超时结束，复制结束");
        } catch (ClosedChannelException e1) {
            log.info("管道关闭，复制结束");
        } catch (Exception e) {
            log.error("管道复制异常结束", e);
        }
        if (end != null)
            end.accept(s1, s2);
    }

    /**
     * 复制一次
     */
    int copy(SocketChannel from, SocketChannel to, BufferLink buffers, BiConsumer<BufferLink, DoublePump> processor) throws IOException {
        buffers.base.clear();
        buffers.clear();
        int n = from.read(buffers.base);
        if (n == -1) {
            log.info("读到-1个字节");
            IOUtil.close(from);
            throw new ClosedChannelException();
        }
        if (n > 0) {
            if (processor != null) { //processor 中需要将 buffer flip
                processor.accept(buffers, this);
            } else buffers.base.flip();
            IOUtil.writeFully(to, buffers.asArray());
        }
        return n;
    }

    static void printBuffer(ByteBuffer b) {
        log.info(new String(b.array(), 0, b.remaining()));
    }
}