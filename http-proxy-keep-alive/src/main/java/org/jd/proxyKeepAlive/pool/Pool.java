package org.jd.proxyKeepAlive.pool;

import org.jd.util.Assert;

import java.util.Deque;
import java.util.LinkedList;

public class Pool<T> {
    private Deque<Pooled<T>> pool = new LinkedList<>();
    private final Factory<Pooled<T>> factory;

    public Pool(Factory<Pooled<T>> factory) {
        this.factory = factory;
    }

    public synchronized Pooled<T> get() {
        Pooled<T> i;
        if (pool.isEmpty()) {
            i = factory.newInstance();
            Assert.isTrue(i.useful, "对象不可用");
            i.pool = this;
        } else i = pool.removeFirst();
        i.idle = false;
        return i;
    }

    protected synchronized void takeBack(Pooled<T> i) {
        if (i.idle || !i.useful)
            return;
        i.idle = true;
        pool.addLast(i);
    }
}