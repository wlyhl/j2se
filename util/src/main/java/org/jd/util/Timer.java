package org.jd.util;

import java.util.Date;

/**
 * Created by cuijiandong on 2018/1/17.
 */
public class Timer {
    private final long start;
    private long last;

    public Timer() {
        start = updateLast();
    }

    public long cost() {
        long tmp = last;
        return updateLast() - tmp;
    }

    public long costCount() {
        return updateLast() - start;
    }

    private long updateLast() {
        return last = new Date().getTime();
    }
}
