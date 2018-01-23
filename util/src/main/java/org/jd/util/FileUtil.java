package org.jd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import static org.jd.util.ExceptionUtil.thro;

/**
 * Created by cuijiandong on 2018/1/15.
 */
public class FileUtil {
    /**
     * 在当前目录查找文件
     * 若找不到，在classPath中查找文件
     */
    public static InputStream find(String path) {
        try {
            File f = new File(path);
            if (f.exists() && f.isFile()) {
                Logger.l.log("find in:", f.getAbsolutePath());
                return new FileInputStream(f);
            }
        } catch (Exception e) {
            thro(e);
        }
        URL url = FileUtil.class.getClassLoader().getResource(path);
        Logger.l.log("find in :",url);
        return FileUtil.class.getClassLoader().getResourceAsStream(path);
    }
}
