package org.jd.util;

/**
 * Created by cuijiandong on 2018/1/15.
 * 抛异常专用工具
 */
public class ExceptionUtil {
    public static <E> E thro(Exception e){
        e.printStackTrace();
        throw new RuntimeException(e);
    }
    public static <E> E thro(String msg){
        throw new RuntimeException(msg);
    }
}
