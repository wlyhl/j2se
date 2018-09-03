package org.jd.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
            f = new File(f.getAbsolutePath());
            if (f.exists() && f.isFile()) {
                Logger.l.log("find in:", f.getAbsolutePath());
                return new FileInputStream(f);
            }
        } catch (Exception e) {
            thro(e);
        }
        URL url = FileUtil.class.getClassLoader().getResource(path);
        Assert.notTrue(url == null, "资源未找到" + path);
        Logger.l.log("find in :", url);
        return FileUtil.class.getClassLoader().getResourceAsStream(path);
    }

    public static void copy(File from, File to) {
        File dir = to.getParentFile();
        Assert.isTrue(dir.isDirectory() || dir.mkdirs(), "路径不正确");
        try (FileOutputStream out = new FileOutputStream(to); FileInputStream in = new FileInputStream(from)) {
            byte[] b = new byte[1024];
            for (int l; (l = in.read(b)) != -1; )
                out.write(b, 0, l);
        } catch (Exception e) {
            ExceptionUtil.thro(e);
        }
    }

    public static void copy(File from, String fileName) {
        copy(from, new File(fileName));
    }
}
