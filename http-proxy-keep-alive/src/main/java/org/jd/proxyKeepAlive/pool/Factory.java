package org.jd.proxyKeepAlive.pool;

public interface Factory<T> {
    T newInstance();
}
