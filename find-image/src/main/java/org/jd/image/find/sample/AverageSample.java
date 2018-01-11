package org.jd.image.find.sample;

import org.jd.image.find.Position;
import org.jd.image.find.Sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class AverageSample implements Sample {

    private int skip;

    /**
     * 平均取样
     * @param skip 取样时跳过的像素个数 1 表示隔1个取一次样
     */
    public AverageSample(int skip) {
        this.skip = skip+1;
    }

    @Override
    public List<Position> sample(int xMax, int yMax) {
        List<Position> pos = new ArrayList<>((xMax+1)*(yMax+1)/skip/skip);
        for(int x=0;x<=xMax;x=skip)
            for(int y=0;y<=yMax;y+=skip)
                pos.add(new Position(x,y));
        return pos;
    }
}
