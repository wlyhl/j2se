package modulo;

import modulo.b.Piece;

/**
 * Created by cuijiandong on 2018/2/12.
 */
public class Wacher extends Thread {
    Piece[] p;

    public Wacher(Piece[] p) {
        this.p = p;
    }

    @Override
    public void run() {
        try {
            while(true){
                System.out.println();
                for (int i = 0; i < p.length; i++) {
                    System.out.print(p[i].pos.x + "" + p[i].pos.x + ",");
                }
                sleep(1000*60*5);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
