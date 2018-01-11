package org.jd.wx.jump;


import java.awt.image.BufferedImage;

/**
 * 缓存图片颜色
 */
public class RGBCache{
    boolean isBanckGround[][];
    public final BufferedImage img;
    private int[][] rgb;

    public RGBCache(BufferedImage img) {
        super();
        this.img = img;
        rgb = new int[img.getWidth()][img.getHeight()];
        isBanckGround = new boolean[img.getWidth()][img.getHeight()];
    }

    public int getRGB(int x, int y) {
        int v = rgb[x][y];
        return v == 0 ? rgb[x][y] = img.getRGB(x, y) : v;
    }

    public void setBackGround(int x, int y) {
        isBanckGround[x][y] = true;
    }
    public void setBackGround(int x, int y,int x2,int y2) {
        final int y_=y;
        do{
            while(y<=y2)
                setBackGround(x,y++);
            y=y_;
        }while(++x<=x2);
    }

    public boolean notBG(int x, int y) {
        return !isBG(x, y);
    }

    public boolean isBG(int x, int y) {
        return isBanckGround[x][y];
    }

}
