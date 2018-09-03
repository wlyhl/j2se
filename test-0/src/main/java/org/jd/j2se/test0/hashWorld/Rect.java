package org.jd.j2se.test0.hashWorld;

import org.jd.image.find.Position;

/**
 * Created by cuijiandong on 2018/3/15.
 */
public class Rect implements Comparable<Rect> {
    public int x, y, w, h;
    int level;//1,2,3

    public Rect(int x, int y, int level) {
        this.x = x;
        this.y = y;
        this.level = level;
    }

    public Rect(Position p, int w, int h, int level) {
        this(p.x, p.y, level);
        this.w = w;
        this.h = h;
    }

    public int y0() {
        return y + h;
    }
    public int zx() {
        return x + w/2;
    }
    public int zy() {
        return y + h/2;
    }

    public boolean equalLine(Rect r) {
        return Math.abs(y - r.y) < 10;
    }

    @Override
    public String toString() {
        return "Rect{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }

    @Override
    public int compareTo(Rect o) {
        if (equalLine(o))
            return Integer.valueOf(x).compareTo(o.x);
        return Integer.valueOf(y).compareTo(o.y);
    }
}
