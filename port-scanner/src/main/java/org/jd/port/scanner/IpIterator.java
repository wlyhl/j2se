package org.jd.port.scanner;

import org.jd.util.lamda.Void_String;

import java.util.Arrays;

/**
 * Created by cuijiandong on 2018/1/5.
 * 192.168.1-5.1-255
 */
public class IpIterator {
    /**
     * @param from     192.168.1.1
     * @param to       192.168.5.255
     * @param callback 回调
     */
    public static void iterate(String from, String to, Void_String callback) {
        int[] f = ip2int(from);
        int[] t = ip2int(to);
        iterate(f, t, callback);
    }

    /**
     *
     * @param ip 192.168.1-5.1-255
     * @param callback 回调
     */
    public static void iterate(String ip, Void_String callback) {
        String[] ips = ip.split("\\.");
        int[] from = new int[ips.length];
        int[] to = new int[ips.length];
        for (int i = 0; i < ips.length; i++) {
            int[] r = convertRange(ips[i]);
            from[i]=r[0];
            to[i]=r[1];
        }
        iterate(from,to,callback);
    }

    /**
     * 范围转为边界
     * @param s "0-10" "5-" "-3" "16"
     * @return [0,10] [5,5] [3,3] [16,16]
     */
    public static int [] convertRange(String s){
        int[] r=new int[2];
        int index = s.indexOf("-");
        if (index >= 0) {// 1-5 -5 11-
            r[0] = Integer.valueOf(index > 0 ? s.substring(0, index) : s.substring(1));
            r[1] = Integer.valueOf(index < s.length() - 1 ? s.substring(index + 1) : s.substring(0, index));
        } else {
            r[0] = Integer.valueOf(s);
            r[1] = r[0];
        }
        return r;
    }
    private static void iterate(int[] f, int[] t, Void_String callback) {
        int[] fSource = Arrays.copyOf(f, f.length);
        for (int i = f.length - 1; ; ) {
            while (f[i] <= t[i]) {
                callback.d(int2ip(f));
                f[i]++;
            }
            while (--i > -1 && f[i] >= t[i]) ;
            if (i == -1) break;
            f[i]++;
            while (++i < f.length)
                f[i] = fSource[i];
            i--;
        }
    }

    private static int[] ip2int(String ip) {
        String[] s = ip.split("\\.");
        int[] i = new int[s.length];
        for (int x = 0; x < s.length; x++)
            i[x] = Integer.valueOf(s[x]);
        return i;
    }

    private static String int2ip(int[] ip) {
        StringBuilder sb = new StringBuilder();
        for (int i : ip)
            sb.append(i).append(".");
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
