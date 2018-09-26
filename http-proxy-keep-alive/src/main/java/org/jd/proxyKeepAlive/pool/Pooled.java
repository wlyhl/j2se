package org.jd.proxyKeepAlive.pool;

public class Pooled<T> {

    public final T instance;
    Pool<T> pool;
    boolean idle;
    boolean useful;

    public Pooled(T instance) {
        this.instance = instance;
        useful = true;
    }

    public void takeBack() {
        pool.takeBack(this);
    }
}
