package org.jd.proxy.nio;

import org.jd.proxy.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;
import java.util.function.Function;

public class Pump extends Thread {
    static Logger log = LoggerFactory.getLogger(Pump.class);
    private SocketChannel from, to;
    private Function<ByteBuffer, ByteBuffer> processor;

    public Pump(SocketChannel from, SocketChannel to) {
        this.from = from;
        this.to = to;
    }

    /**
     * @param from      数据源
     * @param to        数据目的地
     * @param processor 中间处理器
     */
    public Pump(SocketChannel from, SocketChannel to, Function<ByteBuffer, ByteBuffer> processor) {
        this(from, to);
        this.processor = processor;
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
                IOUtil.write(to, wb);
            }
            log.info("管道复制结束");
        } catch (ClosedChannelException e1) {
            log.info("管道被关闭，复制结束");
        } catch (Exception e) {
            log.error("管道复制异常", e);
        } finally {
            IOUtil.close(from, to);
        }
    }

    static void printBuffer(ByteBuffer b) {
        log.info(new String(b.array(), 0, b.remaining()));
    }
}
