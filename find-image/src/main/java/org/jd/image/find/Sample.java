package org.jd.image.find;

import java.util.List;

/**
 * 取样接口
 */
public interface Sample {
    /**
     * 取样
     * @param width 宽度
     * @param height 高度
     * @return 取样点
     */
    List<Position> sample(int width,int height);
}
