package org.jd.j2se.test0.qlcoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by cuijiandong on 2018/3/14.
 * 可视化1
 */
public class DadaView {
    public static void main(String[] a) throws Exception {
        BufferedReader r=new BufferedReader(new FileReader("d:/qlcoder/1.txt"));
        BufferedImage i=new BufferedImage(1000,1000,BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = i.getGraphics();
        g.setColor(Color.WHITE);
        String s=null;
      while ( (s= r.readLine())!=null){
          String[] pos=s.split(" ");
          g.drawLine(Integer.valueOf(pos[0]),Integer.valueOf(pos[1]),Integer.valueOf(pos[0]),Integer.valueOf(pos[1]));
      }
      ImageIO.write(i,"jpg",new File("d:/qlcoder/1.jpg"));
    }
}
