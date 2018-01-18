package org.jd.image.find.sample;

import org.jd.image.find.Position;
import org.jd.image.find.Sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class AverageSample implements Sample {

    private int n;

    /**
     * 平均取样
     *
     * @param n 每n个点取一次样
     */
    public AverageSample(int n) {
        this.n = n;
    }

    /**
     *
     * @param count 大概取样总数，
     * @param width 图片宽度
     * @param height 图片高度
     */
    public AverageSample(int count, double width, double height) {
        this((int) Math.round(Math.sqrt(width * height / count)));
    }

    @Override
    public List<Position> sample(int xMax, int yMax) {
        List<Position> pos = new ArrayList<>((xMax + 1) * (yMax + 1) / n / n);
        for (int y = 0; y <= yMax; y += n)
            for (int x = 0; x <= xMax; x += n)
                pos.add(new Position(x, y));
        return pos;
    }
}
