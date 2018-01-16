package org.jd.hands.free;

import org.jd.image.find.Position;
import org.jd.image.find.TargetImg;
import org.jd.image.find.sample.AverageSample;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import static org.jd.util.ExceptionUtil.thro;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class Robot {
    java.awt.Robot r;
    Clipboard clipboard;
    /**
     * 键鼠操作一次后延时
     */
    private int delay = 15;

    public Robot() {
        try {
            r = new java.awt.Robot();
            r.setAutoDelay(delay);
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        } catch (AWTException e) {
            thro(e);
        }
    }

    /**
     * 鼠标单击
     */
    public void mouseClick() {
        r.mousePress(InputEvent.BUTTON1_MASK);
        delay();
        r.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private LinkedList<Point> mouseHistory = new LinkedList<>();

    /**
     * 鼠标移动
     *
     * @param x x坐标
     * @param y y坐标
     */
    public void mouseMove(int x, int y) {
        mouseHistory.add(MouseInfo.getPointerInfo().getLocation());
        if (mouseHistory.size() > 100)
            mouseHistory.removeFirst();
        r.mouseMove(x, y);
    }

    /**
     * 鼠标后退
     * @param step 后退步数
     */
    public void mouseBack(int step) {
        Point p = null;
        while (step-- > 0 && mouseHistory.size() > 0)
            p = mouseHistory.removeLast();
        r.mouseMove(p.x, p.y);
    }

    /**
     * 在屏幕上找到目标图片并移动到图片附近
     *
     * @param img 准备被查找的目标图片
     * @param x   相对目标图片左上角的x偏移量
     * @param y   相对目标图片左上角的y偏移量
     */
    public void mouseMove(BufferedImage img, int x, int y) {
        TargetImg targetImg = new TargetImg(img, 3, new AverageSample(3));
        Position pos = null;
        for (int i = 0; i < 5 && (pos = targetImg.findIn(screencap())) == null; i++)
            r.delay(1500);
        if (pos != null) {
            pos.x += x;
            pos.y += y;
            mouseMove(pos.x, pos.y);
        } else System.out.println("屏幕上未找到目标图片");
    }

    /**
     * 延时
     *
     * @param delay 延时时间（毫秒）
     */
    public void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            thro(e);
        }
    }

    public void delay() {
        delay(delay);
    }

    public void paste(String s) {
        try {
            Transferable old = clipboard.getContents(null);
            clipboard.setContents(new StringSelection(s), null);
            typeWith(KeyEvent.VK_V, KeyEvent.VK_CONTROL);
            clipboard.setContents(old, null);
        } catch (Exception e) {
            thro(e);
        }
    }

    public void typeWith(int code, int with) {
        r.keyPress(with);
        delay();
        type(code);
        delay();
        r.keyRelease(with);
    }

    public void type(int code) {
        r.keyPress(code);
        delay();
        r.keyRelease(code);
    }

    public void type(String key) {
        switch (key) {
            case "tab":
                type(KeyEvent.VK_TAB);
                break;
            case "enter":
                type(KeyEvent.VK_ENTER);
                break;
        }

    }

    public static final Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    public BufferedImage screencap() {
        return r.createScreenCapture(screenRectangle);
    }
}
