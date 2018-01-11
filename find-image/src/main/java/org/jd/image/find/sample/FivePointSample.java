package org.jd.image.find.sample;

import org.jd.image.find.Position;
import org.jd.image.find.Sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class FivePointSample implements Sample {
    public enum SampleType {
        x, plus
    }

    private SampleType sampleType;
    private float border;

    /**
     * 五点取样
     * @param sampleType x表示取四个角加中间，plus表示取四边中点加中间
     * @param border 距离边缘比例 宽度100，border=0.1表示距离边缘10像素取样
     */
    public FivePointSample(SampleType sampleType, float border) {
        this.sampleType = sampleType;
        this.border = border;
    }

    @Override
    public List<Position> sample(int xMax, int yMax) {
        int border=Float.valueOf(this.border*(yMax+1)).intValue();
        List<Position> pos = new ArrayList<>(5);
        pos.add(new Position(xMax / 2, yMax / 2));//中间
        switch (sampleType) {
            case x:
                pos.add(new Position(border, border));//左上
                pos.add(new Position(xMax - border, border));//右上
                pos.add(new Position(border, yMax - border));//左下
                pos.add(new Position(xMax - border, yMax - border));//右下
                break;
            case plus:
                pos.add(new Position(border, yMax / 2));//左
                pos.add(new Position(xMax - border, yMax / 2));//右
                pos.add(new Position(xMax / 2, border));//上
                pos.add(new Position(xMax / 2, yMax - border));//下
        }
        return pos;
    }
}
