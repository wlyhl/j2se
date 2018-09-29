package org.jd.proxyKeepAlive.pool;

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
            i.pool = this;
        } else {
            i = pool.removeFirst();
        }
        i.idle = false;
        return i;
    }

    synchronized void takeBack(Pooled<T> i) {
        if (i.idle)
            return;
        i.idle = true;
        pool.addLast(i);
    }
}