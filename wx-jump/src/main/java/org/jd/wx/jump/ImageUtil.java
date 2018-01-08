package org.jd.wx.jump;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by cuijiandong on 2018/1/3.
 */
public class ImageUtil {
    static final byte [] pngEnd={73,69,78,68,(byte)174,66,96,(byte)130};
    public static BufferedImage readFromADB(InputStream in) {
        try{
            ByteArrayOutputStream bao=new ByteArrayOutputStream();
            out:for(;;){
                int r = in.read();
                bao.write(r);
                if(r==pngEnd[0]){
                    for(int i=1;i<pngEnd.length;i++){
                        int d = in.read();
                        if((byte)d!=pngEnd[i]){
                            for(int j=1;j<i;j++)
                                bao.write(pngEnd[j]);
                            bao.write(d);
                            continue out;
                        }
                    }
                    bao.write(pngEnd,1,pngEnd.length-1);
                    break;
                }
            }
            byte[] bytes = bao.toByteArray();
            byte[] bytes1=new byte[bytes.length];
            for(int a=0,b=0;a<bytes.length;a++){
                if(bytes[a]==13 && bytes[a+1]==13 && bytes[a+2]==10){
                    a++;
                }else{
                    bytes1[b++]=bytes[a];
                }
            }
            return ImageIO.read(new ByteArrayInputStream(bytes1));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void writeTo(BufferedImage img,String name){
        new Thread(() -> {
            try {
                ImageIO.write(img, "png", new File(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
