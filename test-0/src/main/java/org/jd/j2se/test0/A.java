package org.jd.j2se.test0;


import org.jd.util.ExceptionUtil;
import org.jd.util.ImageUtil;
import org.jd.util.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by cuijiandong on 2018/1/17.
 */
public class A {
    static Logger l=new Logger(System.out);
    static org.jd.util.Robot r=new org.jd.util.Robot();
    public static  void main(String[]a)throws Exception{
//        Timer t=new Timer();

//        l.log("开始",t.cost());
//
//
//        for(int i=0;i<1000;i++){
//            r.screencap();
//            l.log("花费",t.cost());
//        }
//        BufferedImage img = ImageIO.read(new File("/C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"));
        BufferedImage img=r.screencap();
        ImageIO.write(img,"png",new File("/d:/img/img/a.png"));
        ImageIO.write(img,"jpg",new File("/d:/img/img/a.jpg"));
        ImageIO.write(img,"gif",new File("/d:/img/img/a.gif"));
        for(int i=1;i<=10;i++)
            ImageUtil.compressJpeg(img,new FileOutputStream("/d:/img/img/"+i+".jpg"),i/10f);

    }
}
