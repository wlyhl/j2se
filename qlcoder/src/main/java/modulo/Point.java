package modulo;

/**
 * Created by cuijiandong on 2018/2/11.
 */
public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + "," + y + "]";
    }

    public boolean equals(Point p) {
        return p.x == x && p.y == y;
    }
}
