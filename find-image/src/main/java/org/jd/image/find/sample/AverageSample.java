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
    private int cnt;

    private AverageSample(int n, int cnt) {
        this.n = n;
        this.cnt = cnt;
    }

    /**
     * 平均取样
     *
     * @param n 每n个点取一次样
     */
    public static AverageSample per(int n) {
        return new AverageSample(n,0);
    }

    /**
     * @param cnt 大概取样总数
     */
    public static AverageSample count(int cnt) {
        return new AverageSample(0,cnt);
    }


    @Override
    public List<Position> sample(int width, int height) {
        if (n == 0)
            n = (int) Math.round(Math.sqrt(width * height / cnt));
        List<Position> pos = new ArrayList<>(width * height / n / n);
        for (int y = 0; y < height; y += n)
            for (int x = 0; x < width; x += n)
                pos.add(new Position(x, y));
        return pos;
    }
}
