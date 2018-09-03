package org.jd.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import static org.jd.util.ExceptionUtil.thro;

/**
 * 操作鼠标键盘，屏幕截图
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
        delay();
    }

    public void mouseClick(int n) {
        while (n-- > 0)
            mouseClick();
    }

    public void mouseClick(int x, int y) {
        mouseMove(x, y);
        mouseClick();
    }

    public void mouseClick(int x, int y, int n) {
        mouseMove(x, y);
        mouseClick(n);
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
        delay();
    }

    /**
     * 鼠标后退
     *
     * @param step 后退步数
     */
    public void mouseBack(int step) {
        Point p = null;
        while (step-- > 0 && mouseHistory.size() > 0)
            p = mouseHistory.removeLast();
        if (p != null)
            r.mouseMove(p.x, p.y);
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
        delay();
    }

    public void type(int code) {
        r.keyPress(code);
        delay();
        r.keyRelease(code);
        delay();
    }

    public void type(String key) {
        type(keyCode(key));
    }

    public void typeWith(String key, String with) {
        typeWith(keyCode(key), keyCode(with));
    }

    /**
     * 键值与ascii码相同
     */
    private static final boolean[] keyCodeEqualASCII = new boolean[94];

    static {
        keyCodeEqualASCII('[', ']');// [ \ ]
        keyCodeEqualASCII(',', '/');//   , - . /
        keyCodeEqualASCII('0', '9');
        keyCodeEqualASCII('A', 'Z');
        keyCodeEqualASCII[';'] = true;
        keyCodeEqualASCII['='] = true;
    }

    private static void keyCodeEqualASCII(int a, int b) {
        while (a <= b) keyCodeEqualASCII[a++] = true;
    }

    private int keyCode(String key) {
        if (key.length() == 1) {
            int keyCode = key.charAt(0);
            if (keyCodeEqualASCII[keyCode])
                return keyCode;
        }
        switch (key) {
            case "shift":
                return KeyEvent.VK_SHIFT;
            case "ctrl":
                return KeyEvent.VK_CONTROL;
            case "alt":
                return KeyEvent.VK_ALT;

            case "enter":
                return KeyEvent.VK_ENTER;
            case "tab":
                return KeyEvent.VK_TAB;
            case "backSpace":
                return KeyEvent.VK_BACK_SPACE;

            case "esc":
                return KeyEvent.VK_ESCAPE;
            case "space":
                return KeyEvent.VK_SPACE;
            case "pageUp":
                return KeyEvent.VK_PAGE_UP;
            case "pageDown":
                return KeyEvent.VK_PAGE_DOWN;
            case "end":
                return KeyEvent.VK_END;
            case "home":
                return KeyEvent.VK_HOME;
            case "insert":
                return KeyEvent.VK_INSERT;
            case "delete":
                return KeyEvent.VK_DELETE;
            case "printScreen":
                return KeyEvent.VK_PRINTSCREEN;

            case "left":
                return KeyEvent.VK_LEFT;
            case "up":
                return KeyEvent.VK_UP;
            case "right":
                return KeyEvent.VK_RIGHT;
            case "down":
                return KeyEvent.VK_DOWN;

            //以下为小键盘
            case "add":
                return KeyEvent.VK_ADD;// +
            case "subtract":
                return KeyEvent.VK_SUBTRACT;//  -
            case "multiply":
                return KeyEvent.VK_MULTIPLY;//  *
            case "device":
                return KeyEvent.VK_DIVIDE;//  /
            case "decimal":
                return KeyEvent.VK_DECIMAL;//  .
            case "numLock":
                return KeyEvent.VK_NUM_LOCK;

            case "n0":
                return KeyEvent.VK_NUMPAD0;
            case "n1":
                return KeyEvent.VK_NUMPAD1;
            case "n2":
                return KeyEvent.VK_NUMPAD2;
            case "n3":
                return KeyEvent.VK_NUMPAD3;
            case "n4":
                return KeyEvent.VK_NUMPAD4;
            case "n5":
                return KeyEvent.VK_NUMPAD5;
            case "n6":
                return KeyEvent.VK_NUMPAD6;
            case "n7":
                return KeyEvent.VK_NUMPAD7;
            case "n8":
                return KeyEvent.VK_NUMPAD8;
            case "n9":
                return KeyEvent.VK_NUMPAD9;

            default:
                throw new RuntimeException("没有对应的键值" + key);
        }
    }

    public static final Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    public BufferedImage screencap() {
        return r.createScreenCapture(screenRectangle);
    }

    public BufferedImage screencap(int x, int y, int width, int height) {
        return r.createScreenCapture(new Rectangle(x, y, width, height));
    }

    public static void exec(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            ExceptionUtil.thro(e);
        }
    }
//    public BufferedImage screencap_() {
//        type(KeyEvent.VK_PRINTSCREEN);
//        try {
//            return (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
//        } catch (Exception e) {
//            ExceptionUtil.thro(e);
//        }
//        return null;
//    }
}
