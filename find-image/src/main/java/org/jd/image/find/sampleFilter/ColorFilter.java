package org.jd.image.find.sampleFilter;

import org.jd.image.find.Position;
import org.jd.image.find.SampleFilter;
import org.jd.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by cuijiandong on 2018/3/16.
 */
public class ColorFilter implements SampleFilter {
    int rgb;

    public ColorFilter(Color c) {
        this.rgb = c.getRGB();
    }

    @Override
    public List<Position> filt(List<Position> pos, BufferedImage img) {
//        BufferedImage img0 = ImageUtil.copy(img);
//        Graphics g = img0.getGraphics();
        ArrayList<Position> poss = new ArrayList<>();
        for (int i = 0; i < pos.size(); i++) {
            Position p = pos.get(i);
//            g.setColor(Color.green);
            if (img.getRGB(p.x, p.y) != rgb)
                poss.add(p);
//            else g.setColor(Color.red);
//            g.drawLine(p.x, p.y, p.x, p.y);
        }
//        try {
//            ImageIO.write(img0,"png",new File("D:\\img\\hashword\\debug\\0.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return poss;
    }
}
