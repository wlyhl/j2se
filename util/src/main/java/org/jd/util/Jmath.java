package org.jd.util;

import java.util.Random;

/**
 * Created by cuijiandong on 2018/1/8.
 */
public class Jmath {
    private static Random r;

    /**
     * 随机整数，不包含 min，max
     *
     * @param min 下限
     * @param max 上限
     * @return
     */
    public static int randomInt(int min, int max) {
        if (r == null)
            r = new Random();
        return r.nextInt(max - min) + min;
    }

    /**
     * 正态分布
     *
     * @param x 自变量
     * @param u 期望，均数，位置参数，对称轴
     * @param o 离散程度
     * @return 正太概率
     */
    public static double normalDistribution(double x, double u, double o) {
        return 1D / (Math.sqrt(2 * Math.PI) * o) * Math.exp(-(x - u) * (x - u) / 2 / o / o);
//        return 1/Math.sqrt(2*Math.PI)*Math.exp(-x*x/2);
    }

    /**
     * return a < b && b < c;
     */
    public static boolean isAscending(int a, int b, int c) {
        return a < b && b < c;
    }

    public static int toInt(double d) {
        return Double.valueOf(d).intValue();
    }

    public static String toHex(long i) {
        int flag = i < 0 ? -1 : 0;
        StringBuilder sb = new StringBuilder();
        for (int n = 0, bit = 1; i != flag || bit != 0; n++, bit = n & 0x7) {
            if (bit == 0)
                sb.append(' ');
            sb.append(0x1 & i);
            if (i != flag)
                i >>= 1;
        }
        return sb.reverse().toString();
    }
}
