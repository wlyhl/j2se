package org.jd.j2se.test0.apng;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by cuijiandong on 2018/4/3.
 */
public class Alpha {
    public static void main(String[] a) throws IOException {
        Color bg = new Color(245,245,245);
        for (int i = 301; i < 399; i++) {
            File f = new File("D:\\img\\bq/" + i + ".png");
            if(!f.isFile())
                continue;
            BufferedImage img = ImageIO.read(f);
            mixAlpha(img,bg);
            ImageIO.write(img, "png", new File("D:\\img\\bq/w/" + i + ".png"));
        }
//        print(ImageIO.read(new File("D:/img/bq/w/318.png")));
    }
    static void mixAlpha(BufferedImage img,Color bg){
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y), true);
                img.setRGB(x, y, mix(c,bg).getRGB());
            }
        }
    }
    static void print(BufferedImage img){
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y), true);
                System.out.print(StringUtils.leftPad(c.getRed()+" ",4));
            }
            System.out.println();
        }
    }

    static Color mix(Color c, Color bg) {
        int alpha = c.getAlpha();
        float ff = alpha / 255f;//前景色比例
        float fb = 1 - ff;//背景色比例
        return new Color(
                mix(c.getRed(),bg.getRed(),ff,fb),
                mix(c.getGreen(),bg.getGreen(),ff,fb),
                mix(c.getBlue(),bg.getBlue(),ff,fb)//  ,c.getAlpha()
        );
    }

    static int mix(int f, int b, float ff, float fb) {
        int v = (int) (f * ff + b * fb);
        return v > 255 ? 255 : v;
    }
}
