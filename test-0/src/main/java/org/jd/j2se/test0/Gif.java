package org.jd.j2se.test0;

import com.sun.imageio.plugins.gif.*;
import org.jd.util.gif.AnimatedGifEncoder;
import org.jd.util.gif.GifDecoder;
import sun.awt.image.GifImageDecoder;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileCacheImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

/**
 * Created by cuijiandong on 2018/1/26.
 */
public class Gif {

    public static void main(String a[]) throws Exception {
//        redo("D:\\img\\gif\\1\\","1.gif",150,1);
        List<BufferedImage> l=new ArrayList<>();
        for (int i = 301; i < 399; i++) {
            File f=new File("D:\\img\\bq\\w\\"+i+".png");
            if(f.isFile())
                l.add(ImageIO.read(f));
        }
        compose("D:\\img\\bq\\w\\c.gif",l,100);
    }

    static void redo(String path,String fileName, int delay, int jump) {
        try {
            File folder=new File(path+"frames");
            if(!folder.isDirectory())
                folder.mkdir();

            GifDecoder decoder = new GifDecoder();
            decoder.read(new FileInputStream(new File(path+fileName)));
            AnimatedGifEncoder agf = new AnimatedGifEncoder();
            agf.start(path + "delay_" + delay + "_jump_" + jump + fileName );
            agf.setRepeat(0);
            agf.setTransparent(Color.WHITE);
            for (int i = 0, ie = decoder.getFrameCount(); i < ie; i += jump) {
                BufferedImage img = decoder.getFrame(i);
                ImageIO.write(img,"jpg",new File(folder+"/"+i+".jpg"));
                agf.addFrame(img);
                agf.setDelay(delay);
            }
            agf.finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static void compose(String path, java.util.List<BufferedImage> img,int delay){
        AnimatedGifEncoder agf = new AnimatedGifEncoder();
        agf.start(path );
        agf.setRepeat(0);
        agf.setTransparent(new Color(255,255,255,0));
        for (int i = 0; i < img.size(); i ++) {
            agf.addFrame(img.get(i));
            agf.setDelay(delay);
        }
        agf.finish();
    }
}
