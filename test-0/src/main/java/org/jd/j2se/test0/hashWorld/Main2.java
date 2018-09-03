package org.jd.j2se.test0.hashWorld;

import org.jd.image.find.Position;
import org.jd.image.find.Sample;
import org.jd.image.find.SampleFilter;
import org.jd.image.find.TargetImg;
import org.jd.image.find.sample.AverageSample;
import org.jd.image.find.sample.FivePointSample;
import org.jd.image.find.sampleFilter.ColorFilter;
import org.jd.util.Robot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;
import java.util.TreeSet;

import static org.jd.j2se.test0.hashWorld.Main.save;

/**
 * Created by cuijiandong on 2018/3/15.
 */
public class Main2 {
    static int x = 19, y = 178, x1 = 440, y1 = 918;

    public static void main(String[] a) throws Exception {
        Click.globleX = x;
        Click.globleY = y;
        Robot r = new Robot();
//        Sample s = new FivePointSample(FivePointSample.SampleType.x, 0.04f);
        Sample s = AverageSample.count(20);
        SampleFilter f = new ColorFilter(Color.WHITE);
        TargetImg t1 = new TargetImg(ImageIO.read(Main2.class.getClassLoader().getResourceAsStream("hashWorld/1.png")), 10, s, f);
        TargetImg t11 = new TargetImg(ImageIO.read(Main2.class.getClassLoader().getResourceAsStream("hashWorld/11.png")), 10, s, f);
        TargetImg t2 = new TargetImg(ImageIO.read(Main2.class.getClassLoader().getResourceAsStream("hashWorld/2.png")), 10, s, f);
        for (int i = 0; i < 1; i++) {
            System.out.println("第" + i + "轮");
            BufferedImage img = r.screencap(x, y, x1 - x, y1 - y);
//            save(img, i);//新线程保存
            TreeSet<Rect> rects = new TreeSet<>();
            rects.addAll(findAll(t1, img, 1));
            rects.addAll(findAll(t11, img, 1));
            rects.addAll(findAll(t2, img, 2));

            Stack<Click> clicks = new Stack<>();
            Rect prev = rects.first();
            int equalLineNum = 1;
            int far = findFar(rects);
            for (Rect rect : rects) {
                if (prev == rect)
                    continue;
                if (prev.equalLine(rect)) {//同一行
                    equalLineNum++;
                    if (rect.x - prev.x - prev.w > prev.w) {//有空白
                        clicks.push(new Click(prev.zx() + prev.w + far, prev.zy(), prev.level));
                    } else {//与前一个紧邻
                        if (equalLineNum == 3 && rect.x + rect.w + rect.w < img.getWidth())//一行最后一个后面是空白
                            clicks.push(new Click(rect.x + rect.w + rect.w / 2 + far, rect.zy(), rect.level));
                    }
                } else {//下一行
                    equalLineNum = 1;
                    if (rect.x - rect.w > 0) {//新行第一个是空白
                        clicks.push(new Click(rect.x - rect.w / 2 - far, rect.zy(), rect.level));
                    }
                }
                prev = rect;
            }
            if (clicks.isEmpty())
                throw new RuntimeException("结束");
            System.out.println("共" + clicks.size());
            while (!clicks.isEmpty()) {
                Click c = clicks.pop();
//                System.out.println();
//                r.mouseMove(c.x, 600);
                r.mouseMove(c.x, c.y);
                r.mouseClick();
                Thread.sleep(200);
                if (c.cnt == 2)
                    r.mouseClick();
                Thread.sleep(200);
            }
            Thread.sleep(2000);
        }
    }

    static TreeSet<Rect> findAll(TargetImg tar, BufferedImage img, int level) {
        TreeSet<Rect> rects = new TreeSet<>();
        Position p0 = tar.findIn(img);
        if (p0 == null)
            return rects;
        rects.add(new Rect(p0, tar.img.getWidth(), tar.img.getHeight(), level));
        for (int b = 0; b < img.getHeight(); ) {
            for (int a = 0; a < img.getWidth(); a++) {
                Rect last = rects.last();
                Position p = tar.findIn(img, a, b, img.getWidth(), img.getHeight());
                if (p == null) {//此行未找到
                    if (a == 0)
                        return rects;
                    b = last.y0() + 5;
                    break;
                } else {
                    rects.add(new Rect(p, tar.img.getWidth(), tar.img.getHeight(), level));
                    System.out.println("找到" + level + new Rect(p, tar.img.getWidth(), tar.img.getHeight(), level));
                    a = p.x + tar.img.getWidth() + 5;
                }
            }
        }
        return rects;
    }

    private static int findFar(TreeSet<Rect> rects) {
        Rect prev = rects.first();
        int equalLineNum = 1;
        for (Rect rect : rects) {
            if (prev == rect)
                continue;
            if (rect.equalLine(prev) && rect.x - prev.x - prev.w < prev.w)
                return rect.x - prev.x - prev.w;
            prev = rect;
        }
        return 0;
    }
}
