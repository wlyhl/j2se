package org.jd.j2se.test0.apng;


import org.apache.commons.lang3.StringUtils;
import org.jd.util.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/4/2.
 */
public class PNG {
    static int len = 6;

    public static void main(String[] a) throws Exception {
        PlayableList<BufferedImage> pngs = new PlayableList<>(len);
        for (int i = 1; i < 100; i++) {
            File f = new File("D:\\img\\bq\\1" + pad(i) + ".png");
            if (!f.isFile())
                break;
            pngs.add(ImageIO.read(f));
        }
        Logger.l.log("共读取" + pngs.size());
        ArrayList<BufferedImage> pngt = new ArrayList<>(pngs.size());
        BufferedImage base = pngs.get(0);
        int jump = pngs.size() / len;
        for (int i = 0; i < 100; i++) {
            BufferedImage t = new BufferedImage(base.getWidth() * len, base.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            pngt.add(t);
            for (int pi = 0; pi < len; pi++) {
                BufferedImage s = pngs.pNext(pi);
                for (int x = 0; x < s.getWidth(); x++) {
                    for (int y = 0; y < s.getHeight(); y++) {
                        t.setRGB(x + pi * base.getWidth(), y, s.getRGB(x, y));
                    }
                }
            }
            if (i < pngs.size() && i % jump == 0) {
                pngs.play(i / len, 1);
                Logger.l.log("Player ",i / len," play");
            }

            if (pngs.pEnd())
                break;
        }
        for (int i = 0; i < pngt.size(); i++) {
            File f = new File("D:\\img\\bq\\w\\1" + pad(i + 1) + ".png");
            ImageIO.write(pngt.get(i), "png", f);
        }
    }

    static String pad(int i) {
        return StringUtils.leftPad(i + "", 2, '0');
    }
}
