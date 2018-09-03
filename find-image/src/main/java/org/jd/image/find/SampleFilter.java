package org.jd.image.find;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by cuijiandong on 2018/3/16.
 */
public interface SampleFilter {
    List<Position> filt(List<Position> pos, BufferedImage img);
}
