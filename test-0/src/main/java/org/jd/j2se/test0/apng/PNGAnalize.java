package org.jd.j2se.test0.apng;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by cuijiandong on 2018/4/3.
 */
public class PNGAnalize {
    public static void main(String[]a) throws IOException {
        BufferedImage img= ImageIO.read(new File("D:\\img\\bq/101.png"));
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y),true);
                System.out.print(c.getAlpha()+" ");
            }
            System.out.println();
        }
    }
}
