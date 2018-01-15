package org.jd.util;

/**
 * Created by cuijiandong on 2018/1/15.
 */
public class ExceptionUtil {
    public static void thro(Exception e){
        e.printStackTrace();
        throw new RuntimeException(e);
    }
}
