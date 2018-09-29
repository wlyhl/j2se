package org.jd.proxyKeepAlive.nio;

import org.jd.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class Pump extends Thread {
    private static Logger log = LoggerFactory.getLogger(Pump.class);
    private final ByteChannel from, to;
    private ArrayList<Function<ArrayList<ByteBuffer>, ArrayList<ByteBuffer>>> processors;
    private Consumer<Pump> end;

    public Pump(ByteChannel from, ByteChannel to) {
        this.from = from;
        this.to = to;
        processors = new ArrayList<>(2);
    }

    /**
     * 中间处理器
     */
    public Pump addProcessor(Function<ArrayList<ByteBuffer>, ArrayList<ByteBuffer>> processor) {
        processors.add(processor);
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
            ByteBuffer buffer = ByteBuffer.allocate(8192);
            do {
                IOUtil.readAtLeast1(from, buffer);
                buffer.flip();
                IOUtil.writeFully(to, buffer);
            } while (from.isOpen());
        } catch (ClosedChannelException e1) {
            log.info("管道被关闭，复制结束");
        } catch (Exception e) {
            log.error("管道复制异常", e);
        } finally {
            log.info("管道复制结束");
            end.accept(this);
        }
    }

    static void printBuffer(ByteBuffer b) {
        log.info(new String(b.array(), 0, b.remaining()));
    }
}
