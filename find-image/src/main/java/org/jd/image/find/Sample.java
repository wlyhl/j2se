package org.jd.image.find;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 取样接口
 */
public interface Sample {
    List<Position> sample(int xMax,int yMax);
}
