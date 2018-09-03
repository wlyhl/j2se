package org.jd.j2se.test0.qlcoder;

import java.io.*;

/**
 * Created by cuijiandong on 2018/3/14.
 */
public class FileInFile {
    public static void main(String[] a) throws Exception {
        DataInputStream in = new DataInputStream(new FileInputStream("d:/img/rf.data"));
        int b;
        int i=0;
        while ((b = in.read()) != -1) {
            i++;
            System.out.println("第"+i+"张图片，删除标志位："+b);
            switch (b) {//1字节的标记位。4字节的size。标记照片的大小x。x字节，照片文件本身。
                case 0://0代表接下来的照片仍然可用，

                case 1://1代表接下来的照片已经被删除，
                    int x = in.readInt();
                    System.out.println("大小:"+x);
                    byte[] imgData = new byte[x];
                    in.readFully(imgData);
                    FileOutputStream fout=new FileOutputStream("D:\\img\\lt\\"+i+"_"+b+".jpg");
                    fout.write(imgData);
                    break;
                case 2://2代表该物理文件接下来已经没有图片了。
                    return;
            }
        }
    }
}
