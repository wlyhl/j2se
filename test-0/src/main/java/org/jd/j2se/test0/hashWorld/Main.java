package org.jd.j2se.test0.hashWorld;

import org.jd.util.Robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by cuijiandong on 2018/3/15.
 */
public class Main {
    static int x = 44, y = 244, x1 = 454, y1 = 904;
    static int l1 = new Color(255, 242, 158).getRGB();
    static int l2 = new Color(135, 98, 72).getRGB();

    public static void main(String[] a) throws Exception {
        Robot r = new Robot();
        for (int i = 0; i < 100; i++) {
            System.out.println("第" + i + "轮");
            BufferedImage img = r.screencap(x, y, x1 - x, y1 - y);
//            save(img, i);//新线程保存

            Rect[][] rec = getRec(img);
            int left = x1, right = x;//最左边的方块x坐标和最右边方块x坐标，
            for (Rect[] re : rec) {
                if (re == null || re[0] == null)
                    break;
                if (re[0].x < left)
                    left = re[0].x;
                if (re[2].x > right)
                    right = re[2].x;
            }
            int far = (right - left) / 3;

            Stack<Click> click = new Stack<>();
            for (Rect[] re : rec) {
                if (re == null || re[0] == null)
                    break;
                Click c = null;
                for (int j = 0; j < re.length - 1; j++) {
                    int aaa = far * j + left - re[j].x;
                    if (Math.abs(far * j + left - re[j].x) > 10) {
                        c = new Click(x + left + far * j + re[j].w / 2, y + re[j].y + re[j].h / 2, re[j].level);
                        System.out.println(j + "x=" + c.x + "c=" + c.cnt);
                        break;
                    }
                }
                if (c == null && re[3] == null) {
                    c = new Click(x + left + far * 3 + re[2].w / 2, y + re[0].y + re[0].h / 2, re[0].level);
                    System.out.println(3 + "x=" + c.x + "c=" + c.cnt);
                }
                if (c != null)
                    click.push(c);
            }
            if (click.isEmpty())
                throw new RuntimeException("结束");
            while (!click.isEmpty()) {
                Click c = click.pop();
                r.mouseMove(c.x, 600);
//                r.mouseMove(c.x, c.y);
                r.mouseClick();
                Thread.sleep(200);
                if (c.cnt == 2)
                    r.mouseClick();
                Thread.sleep(200);
            }
            Thread.sleep(2000);
        }
    }

    static Rect[][] getRec(BufferedImage img) {
        Rect[][] rec = new Rect[16][4];
        int recX = 0, recY = 0;//坐标
        out:
        for (int b = 0; b < img.getHeight(); b++) {
            for (int a = 0; a < img.getWidth(); a++) {
                int rgb = img.getRGB(a, b);
                if (rgb == l1 || rgb == l2) {
                    Rect rect = new Rect(a, b, rgb == l1 ? 1 : 2);
                    for (int bb = b; bb < img.getHeight() && img.getRGB(a, bb) == rgb; bb++) {
                        rect.h = bb - b;
                        for (int aa = a; aa < img.getWidth() && img.getRGB(aa, bb) == rgb; aa++) {
                            rect.w = aa - a;
                        }
                    }
                    if (rect.w > 20 && rect.h > 20) {
                        if (rec[recY][0] != null && rec[recY][0].y + rec[recY][0].h > rect.x) {//换行
                            recX = 0;
                            b += 10 + rec[recY][0].h;
                            recY++;
                            continue out;
                        }
                        System.out.println("发现方块" + rect);
                        rec[recY][recX] = rect;
                        recX++;
                        a += rect.w + 1;
                    }
                }
            }
        }
        return rec;
    }

    static void save(BufferedImage img, String i) {
        new Thread(() -> {
            try {
                ImageIO.write(img, "png", new File("D:\\img\\hashword\\" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
