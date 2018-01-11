package org.jd.image.find;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class TargetImg {
    private List<Position> pos;
    private List<RGB> rgb = new ArrayList<>();

    public int width;
    private int height;

    /**
     * @param img       目标图片
     * @param deviation 误差 10表示rgb颜色值在10以内视为相同
     * @param sample    抽样器
     */
    public TargetImg(BufferedImage img, int deviation, Sample sample) {
        width = img.getWidth();
        height = img.getHeight();
        pos = sample.sample(width - 1, height - 1);
        for (Position p : pos) {
            rgb.add(new RGB(img.getRGB(p.x, p.y)).setDeviation(deviation));
        }
    }

    public Position findIn(BufferedImage img, int x, int y, int x1, int y1) {
        x1 -= width;
        y1 -= height;
        for (int xOriginal = x - 1; y <= y1; y++, x = xOriginal)
            while (++x <= x1)
                if (like(img, x, y))
                    return new Position(x, y);
        return null;
    }
    public Position findIn(BufferedImage img) {
        return findIn(img,0,0,img.getWidth()-1,img.getHeight()-1);
    }

    private boolean like(BufferedImage img, int x, int y) {
        for (int i = 0; i < pos.size(); i++) {
            Position p = pos.get(i);
            if (!rgb.get(i).like(img.getRGB(x + p.x, y + p.y)))
                return false;
        }
        return true;
    }
}
