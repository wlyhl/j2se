package org.jd.wx.jump;

import org.jd.util.Jmath;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;


public class TargetImg {
    private ArrayList<Position> pos = new ArrayList<>();
    private ArrayList<RGB> rgb = new ArrayList<>();

    public int width;
    private int height;

    public TargetImg(BufferedImage img, int devition) {
        width = img.getWidth();
        height = img.getHeight();
        pos.add(new Position(1, img.getHeight() / 2));//左
        pos.add(new Position(img.getWidth() - 2, img.getHeight() / 2));//右
        pos.add(new Position(img.getWidth() / 2, 1));//上
        pos.add(new Position(img.getWidth() / 2, img.getHeight() - 2));//下
        pos.add(new Position(img.getWidth() / 2, img.getHeight() / 2));//中间

        for (Position p : pos) {
            rgb.add(new RGB(img.getRGB(p.x, p.y)).setDevition(devition));
        }
    }

    public Position foundIn(RGBCache img) {
        for (int y = Jmath.toInt(img.img.getHeight()*0.364),
             xend = img.img.getWidth() - width,
                yEnd=Jmath.toInt(img.img.getHeight()*0.677);
             y < yEnd; y++)
            for (int x = 0; x < xend; x++) {//左上角
                if (like(img, x, y)) {
                    img.setBackGround(x, y, x + width, y + height);
                    Position p = pos.get(3);
                    return new Position(x + p.x, y + p.y);
                }
            }
        return null;
    }

    private boolean like(RGBCache img, int x, int y) {
        for (int i = 0; i < pos.size(); i++) {
            Position p = pos.get(i);
            if (!rgb.get(i).like(img.getRGB(x + p.x, y + p.y)))
                return false;
        }
        return true;
    }

    private float findZoom(RGBCache img) {
        float zoom=1.0f;
        return zoom;
    }
}
