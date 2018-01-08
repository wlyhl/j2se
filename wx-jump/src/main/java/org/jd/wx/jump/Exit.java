package org.jd.wx.jump;

/**
 * Created by cuijiandong on 2018/1/3.
 */
public class Exit {
    public static void exit(String s){
        System.err.println("系统退出，"+s);
        System.exit(1);
    }
    public static void exit(String s,Throwable e){
        e.printStackTrace();
        exit(s);
    }

}
