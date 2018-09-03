package modulo.a;

import modulo.Point;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cuijiandong on 2018/2/11.
 */
public class Piece {
    Point pos;
    Point maxPos;
    Point[] points;
    String s;
    int index;
    int after;

    public Piece(String s, int[][] map, int index, int after) {//XXX,XXX,.XX,XX.,.X.
        this.s = s;
        this.index = index;
        this.after = after;
        ArrayList<Point> ps = new ArrayList<>();
        String[] ss = s.split(",");
        maxPos = new Point(map[0].length - ss[0].length(), map.length - ss.length);
        for (int i = 0; i < ss.length; i++) {
            for (int j = 0; j < ss[i].length(); j++) {
                if (ss[i].charAt(j) == 'X')
                    ps.add(new Point(j, i));
            }
        }
        points = ps.toArray(new Point[ps.size()]);
        pos = new Point(0, 0);
    }

    boolean posAdd() {
        pos.x++;
        if (pos.x > maxPos.x) {
            pos.x = 0;
            pos.y++;
            if (pos.y > maxPos.y) {
                pos.y = 0;
                return false;
            }
        }
        return true;
    }

    public void commit(int[][] map, int modu) {
        for (Point p : points) {
            int x = pos.x + p.x, y = pos.y + p.y;
            int newValue = map[y][x] + 1;
            map[y][x] = newValue == modu ? 0 : newValue;
        }
    }

    public void revert(int[][] map, int modu) {
        for (Point p : points) {
            int x = pos.x + p.x, y = pos.y + p.y;
            int newValue = map[y][x] - 1;
            map[y][x] = newValue == -1 ? modu - 1 : newValue;
        }
    }

    @Override
    public String toString() {
        return s + Arrays.toString(points) + " pos:" + pos;
    }

    private void printMap(int[][] map) {
        StringBuilder sb = new StringBuilder("\n========\n");
        for (int[] i : map) {
            for (int ii : i)
                sb.append(ii).append(" ");
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
