package modulo.b;

import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/2/11.
 */
public class Map {
    int[][] map;
    int[] count;
    int mapSize;
    int modu_1;//modu-1

    public Map(ArrayList<String> maps, int modu) {
        this(new int[maps.size()][], modu);
        for (int i = 0; i < maps.size(); i++) {
            String s = maps.get(i);
            map[i] = new int[s.length()];
            mapSize += map[i].length;
            for (int j = 0; j < s.length(); j++) {
                map[i][j] = s.charAt(j) - '0';
                count[map[i][j]]++;
            }
        }
    }

    public Map(int[][] map, int modu) {
        count = new int[modu];
        this.modu_1 = modu - 1;
        this.map = map;
    }

    public int not0() {
        return mapSize - count[0];
    }

    int plus1(int x, int y) {
//        int old = map[y][x];
//        count[old]--;
//        old = old == modu_1 ? 0 : old + 1;
//        map[y][x] = old;
//        count[old]++;
       return plus(x,y,1,modu_1,0);
    }

    int minus1(int x, int y) {
//        int old = map[y][x];
//        count[old]--;
//        old = old == 0 ? modu_1 : old - 1;
//        map[y][x] = old;
//        count[old]++;
       return plus(x,y,-1,0,modu_1);
    }
    private int plus(int x,int y,int val,int compare,int ifMatch){
        int old = map[y][x];
        count[old]--;
        old = old == compare ? ifMatch : old + val;
        map[y][x] = old;
        count[old]++;
        return old;
    }
}
