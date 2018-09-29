package org.jd.proxyKeepAlive.pool;

public class Pooled<T> {

    public final T instance;
    Pool<T> pool;
    boolean idle;

    public Pooled(T instance) {
        this.instance = instance;
    }

    public void takeBack() {
        pool.takeBack(this);
    }
}
