package org.jd.j2se.test0.adb;


import org.jd.util.Logger;
import org.jd.wx.jump.ImageUtil;
import org.jd.wx.jump.shell.Shell;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cuijiandong on 2018/4/4.
 */
public class ScreenCap {
    public static void main(String[] a) throws IOException {

        for (int i = 0; i < 1000; i++) {
            Logger.l.log("第次循环 ", i);
            Shell shell = new Shell("cmd");
            InputStream in = shell.exe("adb shell screencap -p");
            BufferedImage img = ImageUtil.readFromADB(in);
//            BufferedImage img = ImageIO.read(new File("D:\\img\\bzgame\\11.png"));
            String path = "D:\\img\\bzgame\\wk\\" + i + ".png";
            new Thread(() -> {
                try {
                    ImageIO.write(img, "png", new File(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            out:
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    Color c = new Color(img.getRGB(x, y));
                    if (c.getRed() == 189 && c.getGreen() == 107 && c.getBlue() == 59) {
                        shell.exe("adb shell input tap " + x + " " + y + " ");
                        Logger.l.log("点击", x, " ", y);
                        break out;
                    }
                }
            }
            Logger.l.log("第次循环结束 ", i);
        }
    }

}
