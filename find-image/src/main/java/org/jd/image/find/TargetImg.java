package org.jd.image.find;

import org.jd.util.Assert;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.jd.util.Jmath.toInt;


public class TargetImg {
    public final BufferedImage img;
    private int deviation = 0;
    private List<Position> pos;
    private List<RGB> rgb;

    private Float xZoom;
    private Float yZoom;
    /**
     * 缩放后点的坐标
     */
    private List<Position> zoomedPos;


    /**
     * @param img       目标图片
     * @param deviation 误差 10表示rgb颜色值在10以内视为相同
     * @param sample    抽样器
     */
    public TargetImg(BufferedImage img, int deviation, Sample sample) {
        this(img, deviation, sample, null);
    }

    public TargetImg(BufferedImage img, int deviation, Sample sample, SampleFilter filter) {
        this.img = img;
        setSample(sample);
        if (filter != null)
            pos = filter.filt(pos, img);
        setDeviation(deviation);
    }

    /**
     * 设置rgb误差
     *
     * @param deviation rgb误差
     */
    public void setDeviation(int deviation) {
        Assert.notEmpty(pos, "please set sample or add position before set deviation");
        this.deviation = deviation;
        if (rgb == null)
            rgb = new ArrayList<>();
        for (Position p : pos)
            rgb.add(new RGB(img.getRGB(p.x, p.y)).setDeviation(deviation));
    }

    /**
     * 设置抽样器
     *
     * @param sample
     */
    public void setSample(Sample sample) {
        pos = sample.sample(img.getWidth(), img.getHeight());
    }

    /**
     * 手动添加一个取样点
     */
    public void addPosition(int x, int y) {
        if (pos == null)
            pos = new ArrayList<>();
        pos.add(new Position(x, y));
        if (rgb != null && rgb.size() == pos.size())
            rgb.add(new RGB(img.getRGB(x, y)).setDeviation(deviation));
    }

    /**
     * 在大图中查找目标图片,查找范围为大图的以(x,y)(x1,y1)为对角线的矩形
     *
     * @param bigImg 大图
     * @return 坐标
     */
    public Position findIn(BufferedImage bigImg, int x0, int y0, int x1, int y1) {
        return findIn_(bigImg, x0, y0, x1, y1, pos);
    }

    public Position findIn(BufferedImage bigImg, int x0, int y0) {
        return findIn_(bigImg, x0, y0, bigImg.getWidth() - 1, bigImg.getHeight() - 1, pos);
    }

    /**
     * 在大图中查找目标图片
     */
    public Position findIn(BufferedImage img) {
        return findIn(img, 0, 0, img.getWidth() - 1, img.getHeight() - 1);
    }

    private Position findIn_(BufferedImage bigImg, int x0, int y0, int x1, int y1, List<Position> pos) {
        for (int y = y0, yEnd = y1 - img.getHeight(), xEnd = x1 - img.getWidth(); y <= yEnd; y++)
            for (int x = x0; x <= xEnd; x++)
                if (like(bigImg, x, y, pos))
                    return new Position(x, y);
        return null;
    }

    /**
     * 查找所有坐标
     * 查找过程中会把已经找到的小图位置设为背景色
     */
    public List<Position> findAllIn(BufferedImage big) {
        RGBCache bigCache = null;
        if (big instanceof RGBCache) {
            bigCache = (RGBCache) big;
        }

        List<Position> poss = new ArrayList<>();
        final int xend = big.getWidth() - img.getWidth();
        final int yend = big.getHeight() - img.getHeight();
        for (int y = 0; y < yend; y++) {
            for (int x = 0; x < xend; x++) {
                if (like(big, x, y, pos)) {
                    poss.add(new Position(x, y));
                    if (bigCache != null)
                        bigCache.setBackGround(x, y, x + img.getHeight(), y + img.getHeight());
                }
            }
        }
        return poss;
    }

    /**
     * 缩放查找，查找速度比 findIn 要慢
     *
     * @param bigImg
     */
    public Position zoomFind(BufferedImage bigImg, final int x0, final int y0, final int x1, final int y1) {
        Position find = null;
        if (zoomedPos != null) //用上次缓存的缩放过的位置查找
            find = findIn_(bigImg, x0, y0, x1, y1, zoomedPos);
        if (find != null)
            return find;

        ArrayList<Position> firstPoints = new ArrayList<>();//所有与第一个点颜色相似的集合
        RGB rgb0 = rgb.get(0);
        for (int y = y0, yEnd = y1 - img.getHeight(), xEnd = x1 - img.getWidth(); y <= yEnd; y++)
            for (int x = x0; x <= xEnd; x++) {
                int rgb = bigImg.getRGB(x, y);
                if (rgb0.like(rgb)) {
                    Position<Integer> p = new Position<>(x, y);
                    p.setCompareWith(rgb0.sumDeviation(rgb));//依据颜色相似度排序
                    firstPoints.add(p);
                }
            }
        firstPoints.sort(null);
        Position p0 = pos.get(0);//第一个点
        RGB rgb1 = rgb.get(1);
        for (int i = 1; i < firstPoints.size(); i++) {
            Position p = firstPoints.get(i);
            List<Position> secondPoints = new ArrayList<>();
            float dy = ((float) (p0.y - p.y)) / (p0.x - p.x);//缩放后，每个点与第一个点连线的斜率不变
            for (int x = p.x, y = p.y; x < x1 && y < y1; y = Math.round(y + dy), x++) {
                if (rgb1.like(bigImg.getRGB(x, y))) {
                    Position sp = new Position(x, y);
                    sp.extraData = (float) x / p0.x;
                    secondPoints.add(sp);
                }

            }
        }
        return null;
    }


    public void setZoomedPos(float xZoom, float yZoom) {
        this.xZoom = xZoom;
        this.yZoom = yZoom;
        zoomedPos = new ArrayList<>(pos.size());
        for (Position p : pos) {
            zoomedPos.add(new Position(toInt(p.x * xZoom), toInt(p.y * yZoom)));
        }
    }

    /**
     * 比较在大图的x,y点图片是否相似
     */
    private boolean like(BufferedImage img, int x, int y, List<Position> pos) {
//        if (x == 27 && y == 52) {
//            System.out.println();
//        }
        for (int i = 0; i < pos.size(); i++) {
            Position p = pos.get(i);
            if (!rgb.get(i).like(img.getRGB(x + p.x, y + p.y)))
                return false;
        }
        return true;
    }


}
