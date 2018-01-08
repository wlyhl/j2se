package org.jd.wx.jump;


public class RGBCache {
    boolean isBanckGround[][];
    java.awt.image.BufferedImage img;
    private int[][] rgb;

    public RGBCache(java.awt.image.BufferedImage img) {
        super();
        this.img = img;
        rgb = new int[img.getWidth()][img.getHeight()];
        isBanckGround = new boolean[img.getWidth()][img.getHeight()];
    }

    public int getRGB(int x, int y) {
        int v = rgb[x][y];
        return v == 0 ? rgb[x][y] = img.getRGB(x, y) : v;
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }

    public void setBackGround(int x, int y) {
        isBanckGround[x][y] = true;
    }
    public void setBackGround(int x, int y,int x2,int y2) {
        do{
            while(y<=y2)
                setBackGround(x,y++);
        }while(++x<=x2);
    }

    public boolean notBG(int x, int y) {
        return !isBG(x, y);
    }

    public boolean isBG(int x, int y) {
        return isBanckGround[x][y];
    }

}
