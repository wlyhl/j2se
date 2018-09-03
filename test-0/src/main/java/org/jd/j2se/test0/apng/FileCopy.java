package org.jd.j2se.test0.apng;

import org.apache.commons.lang3.StringUtils;
import org.jd.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/4/3.
 */
public class FileCopy {
    public static void main(String[]a) throws Exception {
        ArrayList<File> l=new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            File f=new File("D:\\img\\bq\\"+ StringUtils.leftPad(""+i,3,'0')+".png");
            if(f.isFile())
                l.add(f);
        }
        for (int i = 0; i < l.size(); i++) {
            FileUtil.copy(l.get(i),"D:\\img\\bq\\w\\p"+ StringUtils.leftPad(i+1+"",3,'0')+".png");
        }
    }
}
