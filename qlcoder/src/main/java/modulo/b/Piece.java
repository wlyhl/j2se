package modulo.b;

import modulo.Point;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cuijiandong on 2018/2/11.
 */
public class Piece {
    public Point pos;
    Point maxPos;
    Point[] points;
    String s;
    int index;
    int after;//后面还有几块
    int afterMin;//后面的能覆盖的最小面积
    int afterMax;//后面的能覆盖的最大面积
    Piece next;
    Piece prev;
    Point answer;
    public Piece(String s, int[][] map, int index) {//XXX,XXX,.XX,XX.,.X.
        this.s = s;
        this.index = index;
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

    public void commit(Map map) {
//        reverted=false;
        for (Point p : points) {
            map.plus1(pos.x + p.x, pos.y + p.y);
        }
    }

    public static String countName[] = {"根据最大最小覆盖面积剪枝", "根据map值剪枝","最后一块piece值"};
    public static int count[] = new int[countName.length];

    public boolean commitAndCheck(Map map) {
        return ck0(map);
    }
    public boolean ck0(Map map) {
        commit(map);
        if (map.modu_1 == 1) {//modu == 2
            return map.count[1] >= afterMin && map.count[1] <= afterMax || FALSE(0);
        } else {//modu > 2
            if (map.not0() > afterMax || map.not0() < afterMin)
                return FALSE(0);
//            if (after > 0 && after < map.modu_1) {
//                for (int i = map.modu_1 - after; i > 0; i--)
//                    if (map.count[i] > 0)
//                        return FALSE(1);
//            }
        }
        return true;
    }
    public boolean ck1(Map map) {
        for (Point p : points) {
            int c=map.plus1(pos.x + p.x, pos.y + p.y);
            if(next == null && c>0)
                count[2]++;
        }
        Boolean ret = true;

        if (map.modu_1 == 1) {//modu == 2
            ret = map.count[1] >= afterMin && map.count[1] <= afterMax || FALSE(0);
        } else {//modu > 2
            if (map.not0() > afterMax || map.not0() < afterMin)
                ret = FALSE(0);
        }
        return ret;

    }

    private boolean FALSE(int i) {
        count[i]++;
        return false;
    }
    public void revert(Map map) {
        for (Point p : points) {
            map.minus1(pos.x + p.x, pos.y + p.y);
        }
    }

    @Override
    public String toString() {
        return s + Arrays.toString(points) + " pos:" + pos + "afterMax:" + afterMax + "afterMin:" + afterMin;
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
