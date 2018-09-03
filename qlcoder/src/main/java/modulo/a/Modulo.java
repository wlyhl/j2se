package modulo.a;

import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/2/11.
 */
public class Modulo {
    public int level;
    int modu;
    int[][] map;
    Piece[] pieces;
    public void test(){
        for (int i = 0; i < pieces.length; i++) {
            pieces[i].commit(map,modu);
        }
        out:while(check()){
            for (int i = pieces.length-1; i >=0 ; i--) {
                Piece p=pieces[i];
                p.revert(map,modu);
                if(p.posAdd()){
                    while(i < pieces.length)
                        pieces[i++].commit(map,modu);
                    continue out;
                }
            }
            System.out.println("未找到答案");
            return;
        }
        System.out.println("找到答案");
        for(Piece p:pieces){
            System.out.print(p.pos.y+""+p.pos.x);
        }
    }
    private boolean check(){
        for (int[] aMap : map) {
            for (int anAMap : aMap) {
                if (anAMap > 0)
                    return true;
            }
        }
        return false;
    }


    public void setModu(String modu) {
        this.modu = Integer.valueOf(modu);
    }

    public void setMap(ArrayList<String> maps) {
        map = new int[maps.size()][];
        for (int i = 0; i < maps.size(); i++) {
            String s = maps.get(i);
            map[i]=new int[s.length()];
            for (int j = 0; j < s.length(); j++) {
                map[i][j]=s.charAt(j)-'0';
            }
        }
    }

    public void setPieces(ArrayList<String> ps) {
        pieces = new Piece[ps.size()];
        for (int i = 0; i < ps.size(); i++) {
            pieces[i]=new Piece(ps.get(i),map,i,ps.size()-i-1);
        }
    }
}
