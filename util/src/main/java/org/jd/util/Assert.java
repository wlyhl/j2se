package org.jd.util;

/**
 * Created by cuijiandong on 2018/1/23.
 */
public class Assert {
    public static void notEmpty(String... ss) {
        String msg = ss[ss.length - 1];
        for (String s : ss)
            ifNotEmpty(s, msg);
    }

    /**
     * if value == null or value == ""
     * throw runtime exception
     * @param value value
     * @param msg msg of exception
     * @return the value if value != null && value != ""
     */
    public static String ifNotEmpty(String value, String msg) {
        if (value == null || value.length() == 0)
            throw new RuntimeException(msg);
        return value;
    }
    public static int isInt(String s,String msg){
        try{
            return Integer.valueOf(s);
        }catch (Exception e){
            throw new RuntimeException(msg);
        }
    }
}
