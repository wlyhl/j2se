package org.jd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static org.jd.util.ExceptionUtil.thro;
/**
 * Created by cuijiandong on 2018/1/15.
 */
public class FileUtil {
    /**
     * 在当前目录查找文件
     * 若找不到，在classPath中查找文件
     */
     public static InputStream find(String path){
        try{
            File f=new File(path);
            if(f.exists()&&f.isFile())
                return new FileInputStream(f);
        }catch (Exception e){
            thro(e);
        }
        return FileUtil.class.getClassLoader().getResourceAsStream(path);
    }
}
