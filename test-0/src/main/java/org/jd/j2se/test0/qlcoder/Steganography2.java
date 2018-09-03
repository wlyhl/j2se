package org.jd.j2se.test0.qlcoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by cuijiandong on 2018/3/14.
 */
public class Steganography2 {
    public static void main(String[] a) throws IOException {
        BufferedImage img = ImageIO.read(new File("d:/img/img_tm.jpg"));
//        BufferedImage img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
//        Graphics g = img2.getGraphics();
//        g.setColor(Color.red);
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int red = new Color(img.getRGB(j, i)).getRed() & 1;
                System.out.print(red == 0 ? "0" : "1");
//                if (red == 1)
//                    g.drawLine(j, i, j, i);
            }
            System.out.println();
        }
//        ImageIO.write(img2,"png",new File("d:/img/lenna_red.png"));
    }
}
