package org.jd.proxyKeepAlive.nio;

import org.jd.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.ClosedChannelException;
import java.util.function.Consumer;
import java.util.function.Function;

public class Pump extends Thread {
    static Logger log = LoggerFactory.getLogger(Pump.class);
    private ByteChannel from, to;
    private Function<ByteBuffer, ByteBuffer> processor;
    private Consumer end;

    public Pump(ByteChannel from, ByteChannel to) {
        this.from = from;
        this.to = to;
    }

    /**
     * 中间处理器，一般做加解密操作
     */
    public Pump setProcessor(Function<ByteBuffer, ByteBuffer> processor) {
        this.processor = processor;
        return this;
    }

    /**
     * 在复制结束时的回调
     */
    public Pump setEnd(Consumer end) {
        this.end = end;
        return this;
    }

    @Override
    public void run() {
        try {
            ByteBuffer rb = ByteBuffer.allocate(8192);
            ByteBuffer wb = rb;
            for (int n = from.read(rb); n > 0; rb.clear(), n = from.read(rb)) {
                rb.flip();
                if (processor != null)
                    wb = processor.apply(rb);
                IOUtil.writeFully(to, wb);
            }
            log.info("管道复制结束");
        } catch (ClosedChannelException e1) {
            log.info("管道被关闭，复制结束");
        } catch (Exception e) {
            log.error("管道复制异常", e);
        } finally {
            end.accept(null);
        }
    }

    static void printBuffer(ByteBuffer b) {
        log.info(new String(b.array(), 0, b.remaining()));
    }
}
