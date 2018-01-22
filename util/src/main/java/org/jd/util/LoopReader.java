package org.jd.util;

import org.jd.util.lamda.Boolean_String;
import org.jd.util.lamda.Void_String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by cuijiandong on 2018/1/22.
 */
public class LoopReader extends BufferedReader {
    private String prevLine;

    public LoopReader(Reader in) {
        super(in);
    }

    /**
     * 如果表达式成立，就继续读
     * 一直读到表达式 == false，
     * 下一次readLine 会得到上次 表达式 == false 时的结果
     * @param while_ 表达式，while_.d()==true 则继续读
     * @param doSth 回调，做一些事情
     * @throws IOException
     */
    public void readWhile(Boolean_String while_, Void_String doSth) throws IOException {
        String s;
        while (while_.d(s = readLine()))
            if(doSth!=null)
                doSth.d(s);
        prevLine=s;
    }
    /**
     * 如果表达式不成立，就继续读
     * 一直读到表达式成立，
     * 下一次readLine 不会得到上次表达式成立时的结果
     * @param until 表达式，until.d()==true 则停止读
     * @throws IOException
     */
    public void readUntil(Boolean_String until) throws IOException {
        while (!until.d(readLine()));
    }

    @Override
    public String readLine() throws IOException {
        String s=prevLine;
        if(prevLine!=null){
            prevLine=null;
            return s;
        }
        return super.readLine();
    }
}
