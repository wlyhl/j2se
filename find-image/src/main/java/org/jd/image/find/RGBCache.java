package org.jd.image.find;


import java.awt.image.BufferedImage;

/**
 * 缓存图片颜色
 */
public class RGBCache extends BufferedImage {
    private int[][] rgb;
    public static int BACK_GROUND_RGB = 1;

    public RGBCache(BufferedImage img) {
        super(img.getWidth(), img.getHeight(), img.getType());
        setData(img.getData());
        rgb = new int[getHeight()][getWidth()];
    }

    @Override
    public int getRGB(int x, int y) {
        int v = rgb[y][x];
        return v == 0 ? rgb[y][x] = super.getRGB(x, y) : v;
    }

    public void setBackGround(int x, int y) {
        rgb[y][x] = BACK_GROUND_RGB;
    }

    public void setBackGround(int x, int y, int x2, int y2) {
        final int y_ = y;
        do {
            while (y < y2)
                setBackGround(x, y++);
            y = y_;
        } while (++x < x2);
    }

    public void setBackGround(BackGroundJudger backGroundJudger) {
        for (int y = 0; y < rgb.length; y++)
            for (int x = 0; x < rgb[y].length; x++)
                if (backGroundJudger.isBackGround(getRGB(x, y)))
                    setBackGround(x, y);
    }

    public interface BackGroundJudger {
        boolean isBackGround(int rgb);
    }
}
