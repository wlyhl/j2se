package org.jd.j2se.test0;

import java.util.Date;
import java.util.Random;

/**
 * Created by cuijiandong on 2018/4/27.
 */

public class CpuTester {
    public static void main(String[] a) {
        int c = 1000000000;
//        new CpuTester((s) -> System.out.println("单线程测试" + s)).test(c);
        for (int i = 0; i < 4; i++) {
            final int j = i;
            new Thread(() -> new CpuTester((s) -> System.out.println("线程" + j + s)).test(c)).start();
        }

    }

    Printer printer;

    public CpuTester(Printer p) {
        printer = p;
    }

    volatile long startTime;

    void start() {
        startTime = new Date().getTime();
    }

    long cost() {
        return new Date().getTime() - startTime;
    }

    public long intAdd(int count) {
        start();
        int c = new Random().nextInt();
        for (int i = 0; i < count; i++) {
            c += i;
            c += i;
        }
        consume(c);
        return cost();
    }

    public long floatAdd(int count) {
        start();
        float c = Integer.MAX_VALUE + 0.5354138635135465f;
        for (int i = 0; i < count; i++) {
            c += i;
        }
        consume(c);
        return cost();
    }

    public long intMultiple(int count) {
        start();
        int c = Short.MAX_VALUE;
        for (int i = c, j = i + count; i < j; i++) {
            c = c * j;
        }
        consume(c);
        return cost();
    }

    public long floatMultiple(int count) {
        start();
        float c = new Random().nextFloat();
        boolean b = true;
        for (int i = Short.MAX_VALUE, j = i + count; i < j; i++) {
            c = (b = !b) ? c * i : c / i;
        }
        consume(c);
        return cost();
    }

    public void test(int count) {
        printer.print("intAdd:" + intAdd(count));
//        printer.print("floatAdd:" + floatAdd(count));
//        printer.print("intMultiple:" + intMultiple(count));
//        printer.print("floatMultiple:" + floatMultiple(count));
    }

    static void consume(Number n) {
        System.out.println(n);
    }
}

interface Printer {
    void print(String s);
}
