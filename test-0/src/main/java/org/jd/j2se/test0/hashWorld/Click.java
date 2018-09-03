package org.jd.j2se.test0.hashWorld;

/**
 * Created by cuijiandong on 2018/3/15.
 */
public class Click {
    static int globleX = 0, globleY = 0;
    int x, y, cnt;

    public Click(int x, int y, int c) {
        this.x = x + globleX;
        this.y = y + globleY;
        this.cnt = c;
    }
}
