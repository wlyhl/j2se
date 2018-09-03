package org.jd.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cuijiandong on 2018/1/17.
 */
public class ImageUtil {
    public static void compressJpeg(BufferedImage img, OutputStream out,float quality){
        ImageWriter w = ImageIO.getImageWritersBySuffix("jpg").next();
        ImageWriteParam p = w.getDefaultWriteParam();
        p.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        p.setCompressionType(p.getCompressionTypes()[0]);
        p.setCompressionQuality(quality);
        try {
            w.setOutput(ImageIO.createImageOutputStream(out));
            w.write(null, new IIOImage(img, null, null), p);
        } catch (IOException e) {
            ExceptionUtil.thro(e);
        }
    }
    public static BufferedImage copy(BufferedImage source){
        BufferedImage img=new BufferedImage(source.getWidth(),source.getHeight(),source.getType());
        img.setData(source.getData());
        return img;
    }
}
