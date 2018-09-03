package modulo.b;

import modulo.Point;
import modulo.Wacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by cuijiandong on 2018/2/11.
 */
public class Modulo {
    public int level;
    int modu;
    Map map;
    Piece[] pieces;

    public void test() {
        for (int i = 0; i < pieces.length; i++) {
            pieces[i].commit(map);
        }
        out:
        while (map.count[0] != map.mapSize) {
            in:
            for (int i = pieces.length - 1; i >= 0; i--) {
                Piece p = pieces[i];
                p.revert(map);
                if (p.posAdd()) {
                    while (i < pieces.length) {
                        if (!pieces[i++].commitAndCheck(map))
                            continue in;//检查发现明显不合适继续posAdd
                    }
                    continue out;
                }
            }
            System.out.println("未找到答案");
            return;
        }
        System.out.println("找到答案");
        Arrays.sort(pieces, Comparator.comparingInt(p2 -> p2.index));
        for (Piece p : pieces) {
            System.out.print(p.pos.y + "" + p.pos.x);
        }
    }


    public void setModu(String modu) {
        this.modu = Integer.valueOf(modu);
    }

    public void setMap(ArrayList<String> maps) {
        map = new Map(maps,modu);
    }
    static String answer="";//25
    public void setPieces(ArrayList<String> ps) {
        pieces = new Piece[ps.size()];
        for (int i = 0; i < ps.size(); i++) {
            pieces[i] = new Piece(ps.get(i), map.map, i);
            if(!"".equals(answer)){
                pieces[i].answer=new Point(answer.charAt(i*2+1)-'0',answer.charAt(i*2)-'0');
            }
        }

        Arrays.sort(pieces, Comparator.comparingInt(p2 -> -p2.points.length));//按照每块点的数量由多到少排序
        for (int i = 0; i < pieces.length; i++) {
            pieces[i].after = pieces.length - i - 1;
            if (i < pieces.length - 1){
                pieces[i].next = pieces[i + 1];
                pieces[i + 1].prev=pieces[i];
            }
        }
        new Wacher(pieces).start();
        boolean flag=true;
        int aftermax = map.mapSize * 2 / 3;
        for (int i = pieces.length-2; i >=0; i--) {
            Map map_=new Map(new int[map.map.length][map.map[0].length],modu);
            Piece p=pieces[i];
            for (int j = pieces.length - 1; j >i; j--)
                pieces[j].commit(map_);
            p.afterMax=map_.not0();
            p.afterMin=map_.not0();
            out: while(flag){
                for (int j = pieces.length - 1; j >i; j--) {//第i+1个到最后一个Piece的全排列
                    pieces[j].revert(map_);
                    if (pieces[j].posAdd()) {
                        while (j < pieces.length)
                            pieces[j++].commit(map_);
                        if(map_.not0()>p.afterMax)
                            p.afterMax=map_.not0();
                        if(map_.not0()<p.afterMin)
                            p.afterMin=map_.not0();
                        continue out;
                    }
                }
                break;
            }
            if(p.afterMax>=aftermax){
                flag=false;
            }
            if(!flag){
                p.afterMax=map.map.length*map.map[0].length;
                p.afterMin=p.next.afterMin;
            }
        }

        for(Piece p:pieces)
            System.out.println(p);
    }
}
