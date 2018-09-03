package org.jd.j2se.test0.hashWorld;

import org.jd.image.find.*;
import org.jd.image.find.sample.AverageSample;
import org.jd.image.find.sampleFilter.ColorFilter;
import org.jd.util.Robot;
import sun.swing.ImageCache;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Stack;
import java.util.TreeSet;

import static org.jd.j2se.test0.hashWorld.Main.save;

/**
 * Created by cuijiandong on 2018/3/15.
 */
public class Main3 {
    static int x = 0, y = 346, x1 = 411, y1 = 1036;
    static boolean debug = false;

    //188 262  13 196  426 887
    public static void main(String[] a) throws Exception {
        Robot r = new Robot();
        TargetImg title = new TargetImg(ImageIO.read(Main3.class.getClassLoader().getResourceAsStream("hashWorld/title.png")), 10, AverageSample.count(30));
        Position pos0 = null;
        if (!debug) {
            pos0 = title.findIn(r.screencap(0, 0, 800, 1000));
            if (pos0 == null)
                throw new RuntimeException("在地区选择页打开");
            x = pos0.x - (188 - 13);
            y = pos0.y - (262 - 196);
            x1 = x + (426 - 13);
            y1 = y + (887 - 196);
        }


        Click.globleX = x;
        Click.globleY = y;

        Sample s = AverageSample.count(200);
        SampleFilter f = new ColorFilter(Color.WHITE);
        TargetImg t1 = new TargetImg(ImageIO.read(Main3.class.getClassLoader().getResourceAsStream("hashWorld/1.png")), 10, s, f);
        TargetImg t11 = new TargetImg(ImageIO.read(Main3.class.getClassLoader().getResourceAsStream("hashWorld/11.png")), 50, s, f);
        TargetImg t2 = new TargetImg(ImageIO.read(Main3.class.getClassLoader().getResourceAsStream("hashWorld/2.png")), 10, s, f);
        System.out.println("等待游戏开始");
        while (!debug) {
            pos0 = t1.findIn(r.screencap(x, y, x1 - x, y1 - y));
            if (pos0 != null)
                break;
            Thread.sleep(500);
        }

        for (int i = 0; i < (debug ? 3 : 10000); i++) {
            System.out.println("第" + i + "轮");
            long time = new Date().getTime();
            BufferedImage img = r.screencap(x, y, x1 - x, y1 - y);
            if (debug) {
//                img = ImageIO.read(new File("D:\\img\\hashword\\186点击_3_0_1_3_2_3_.png"));
                img = r.screencap(x, y, x1 - x, y1 - y);
            }

            RGBCache rgbCache = new RGBCache(img);
            System.out.println("截图耗时" + (new Date().getTime() - time));
            time = new Date().getTime();

            TreeSet<Rect> rects = new TreeSet<>();
            rects.addAll(findAll(t1, rgbCache, 1));
            rects.addAll(findAll(t11, rgbCache, 1));
            rects.addAll(findAll(t2, rgbCache, 2));
            if (far == 0)//初始化方块之间的距离
                init(rects);

            Stack<Rect[]> stack = new Stack<>();
            for (Rect rect : rects) {
                if (topEqualLine(stack, rect)) {
                    stack.peek()[getRectPos(rect)] = rect;
                } else {
                    Rect[] rects1 = new Rect[4];
                    rects1[getRectPos(rect)] = rect;
                    stack.push(rects1);
                }
            }
            System.out.println("计算耗时" + (new Date().getTime() - time));

            if (stack.isEmpty()){
                save(img, i+"");//新线程保存
                throw new RuntimeException("结束");
            }

            System.out.println("共" + stack.size() + "行");
            StringBuilder pos = new StringBuilder("点击_");
            while (!stack.isEmpty()) {
                Rect[] rect = stack.pop();
                boolean hasLvl2 = false;
                for (int j = 0; j < rect.length; j++) {
                    if (rect[j] != null && rect[j].level == 2) {
                        hasLvl2 = true;
                        break;
                    }
                }
                boolean lost = false;
                for (int j = 0; j < rect.length; j++) {
                    if (rect[j] != null) {
                        continue;
                    }
                    lost = true;
                    if (!debug)
                        r.mouseClick(x + leftX + j * (rectWith + far) + rectWith / 2, 600, hasLvl2 ? 2 : 1);
                    pos.append(j + "_").append(hasLvl2 ? j + "_" : "");
                }
                if (!lost && hasLvl2) {//一行四个，且有二级方块
                    for (int j = 0; j < rect.length; j++) {
                        if (rect[j].level == 1) {
                            if (!debug)
                                r.mouseClick(rect[j].x + rectWith / 2, 600);
                            pos.append(j + "_");
                        }
                    }
                }else//等待该行消失
                    r.delay();
            }
            System.out.println(pos);
            save(img, i + pos.toString());//新线程保存
            Thread.sleep(370);

        }
    }

    static boolean topEqualLine(Stack<Rect[]> stack, Rect rect) {
        if (stack.isEmpty())
            return false;
        for (Rect r : stack.peek()) {
            if (r != null && r.equalLine(rect))
                return true;
        }
        return false;
    }

    static int getRectPos(Rect rect) {
        float f = rect.x - leftX;
        return Math.round(f / (rect.w + far));
    }

    static TreeSet<Rect> findAll(TargetImg tar, RGBCache img, int level) {
        TreeSet<Rect> rects = new TreeSet<>();
        for (Position p : tar.findAllIn(img)) {
            if (debug)
                System.out.println(p);
            rects.add(new Rect(p, tar.img.getWidth(), tar.img.getHeight(), level));
        }
        return rects;
    }

    static int far = 0,//两个方块之间的距离
            leftX = x1,//最左边方块x坐标
            rectWith = 0;

    private static void init(TreeSet<Rect> rects) {
        Rect prev = rects.first();
        rectWith = prev.w;
        for (Rect rect : rects) {
            if (leftX > rect.x) {
                leftX = rect.x;
            }
            if (prev == rect)
                continue;
            if (far == 0 && rect.equalLine(prev) && rect.x - prev.x - prev.w < prev.w) {
                far = rect.x - prev.x - prev.w;
            }
            prev = rect;
        }
        System.out.println("leftX = " + leftX + " far = " + far);
    }
}
