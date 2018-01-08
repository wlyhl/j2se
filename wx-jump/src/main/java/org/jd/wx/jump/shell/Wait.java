package org.jd.wx.jump.shell;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.concurrent.Callable;

/**
 * Created by cuijiandong on 2018/1/5.
 */
public class Wait implements Callable<InputStream> {
    InputStream in;
    LinkedList<String> str=new LinkedList<>();

    public Wait(InputStream in) {
        this.in = in;
    }
    public Wait addWaitString(String...s){
        for(String ss:s)
            str.addLast(ss);
        return this;
    }

    @Override
    public InputStream call() throws Exception {
        BufferedReader r=new BufferedReader(new InputStreamReader(in,Charset.defaultCharset()));
        while(!str.isEmpty()){
            String s = r.readLine();
            String first = str.getFirst();
            if(first.equals("")?s.equals(""):s.contains(first)){
                str.removeFirst();
            }
        }
        return in;
    }
}
