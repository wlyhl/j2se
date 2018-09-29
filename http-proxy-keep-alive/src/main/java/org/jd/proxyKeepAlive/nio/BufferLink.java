package org.jd.proxyKeepAlive.nio;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by cuijiandong on 2018/9/29.
 */
public class BufferLink {
    public LinkedList<ByteBuffer> prev = new LinkedList<>();
    public LinkedList<ByteBuffer> next = new LinkedList<>();
    public final ByteBuffer base;

    public BufferLink(ByteBuffer base) {
        this.base = base;
    }

    ByteBuffer[] asArray() {
        ArrayList<ByteBuffer> list = new ArrayList<>(prev.size() + next.size() + 1);
        list.addAll(prev);
        list.add(base);
        list.addAll(next);
        return list.toArray(new ByteBuffer[list.size()]);
    }

    void clear() {
        prev.clear();
        next.clear();
    }

}
