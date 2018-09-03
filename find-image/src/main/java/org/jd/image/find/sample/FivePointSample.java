package org.jd.image.find.sample;

import org.jd.image.find.Position;
import org.jd.image.find.Sample;
import org.jd.util.Assert;

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
     *
     * @param sampleType x表示取四个角加中间，plus表示取四边中点加中间
     * @param border     距离边缘比例 宽度100，border=0.1表示距离边缘10像素取样
     */
    public FivePointSample(SampleType sampleType, float border) {
        this.sampleType = sampleType;
        this.border = border;
    }

    @Override
    public List<Position> sample(int width, int height) {
        Assert.notTrue(border >= 0.5, "边界不能大于50%");
        int l = Float.valueOf(border * width).intValue();//left
        Assert.isTrue(width - l * 2 > 3, "边界太宽");
        int u = Float.valueOf(border * height).intValue();//up
        Assert.isTrue(height - u * 2 > 3, "边界太宽");
        int d = height - 1 - u, r = width - 1 - l;//down,right
        List<Position> pos = new ArrayList<>(5);
        pos.add(new Position(width / 2, height / 2));//center
        switch (sampleType) {
            case x:
                pos.add(new Position(l, u));//左上
                pos.add(new Position(r, u));//右上
                pos.add(new Position(l, d));//左下
                pos.add(new Position(r, d));//右下
                break;
            case plus:
                pos.add(new Position(l, height / 2));//左
                pos.add(new Position(r, height / 2));//右
                pos.add(new Position(width / 2, u));//上
                pos.add(new Position(width / 2, d));//下
        }
        return pos;
    }
}
