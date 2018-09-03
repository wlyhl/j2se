package org.jd.util;

import java.util.Collection;

import static org.jd.util.ExceptionUtil.thro;

/**
 * Created by cuijiandong on 2018/1/23.
 */
public class Assert {
    public static void notEmpty(String... ss) {
        String msg = ss[ss.length - 1];
        for (String s : ss)
            ifNotEmpty(s, msg);
    }

    public static <E> void notEmpty(Collection<E> c, String msg) {
        if (c == null || c.isEmpty())
            thro(msg);
    }

    /**
     * if value == null or value == ""
     * throw runtime exception
     *
     * @param value value
     * @param msg   msg of exception
     * @return the value if value != null && value != ""
     */
    public static String ifNotEmpty(String value, String msg) {
        if (value == null || value.length() == 0)
            thro(msg);
        return value;
    }

    public static int isInt(String s, String msg) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            return thro(msg);
        }
    }

    public static void isTrue(boolean b, String msg) {
        notTrue(!b, msg);
    }

    public static void notTrue(boolean b, String msg) {
        if (b) thro(msg);
    }

    public static <E> int sizeEqual(String msg, Collection<E>... c) {
        int size = c[0] == null ? thro(msg) : c[0].size();
        for (int i = 1; i < c.length; i++)
            if (c[i].size() != size)
                thro(msg);
        return size;
    }

}
