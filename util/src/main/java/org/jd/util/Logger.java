package org.jd.util;

import java.io.OutputStream;

/**
 * Created by cuijiandong on 2018/1/17.
 */
public class Logger {
    OutputStream out;

    public Logger(OutputStream out) {
        this.out = out;
    }
    public void log(Object... s){
        try{
            for(Object ss:s){
                out.write(ss.toString().getBytes());
            }
            out.write('\n');
        }catch (Exception e){
            ExceptionUtil.thro(e);
        }
    }
}
