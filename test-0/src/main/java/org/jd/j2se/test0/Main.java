package org.jd.j2se.test0;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by cuijiandong on 2018/5/25.
 */

public class Main {
    public static void main(String[] a) {
        try {
            while (true) {
                String[] n = new String[Integer.valueOf(next())];
                for (int i = n.length - 1; i >= 0; i--) {
                    n[i] = next();
                }
                Arrays.sort(n);
                for (int i = n.length - 1; i >= 0; i--) {
                    String s = n[i];
                    if (s == null)
                        continue;
                    String next = null;
                    for (int j = i - 1; j >= 0 && (next = n[j]) == null; j--) ;
                    if (i == 0 || next==null || s.length()==next.length())
                        System.out.print(s);
                    else {

                    }
                }

                System.out.println();
            }
        } catch (Exception e) {
        }
    }

    static String next() throws IOException {
        StringBuilder sb = new StringBuilder();
        int i = System.in.read();
        if (i <= 0)
            throw new IOException();
        char c = (char) i;
        while (c < '0' || c > '9') {
            c = (char) System.in.read();
        }
        sb.append(c);
        do {
            c = (char) System.in.read();
            sb.append(c);
        } while (c >= '0' && c <= '9');
        sb.setLength(sb.length() - 1);
        return sb.toString();

    }
}
//class C implements Comparator<String>{
//    int index;
//
//    public C(int index) {
//        this.index = index;
//    }
//
//    @Override
//    public int compare(String s1, String s2) {
//
//    }
//}
