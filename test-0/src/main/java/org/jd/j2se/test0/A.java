package org.jd.j2se.test0;


import jdk.net.NetworkPermission;
import org.apache.commons.lang3.StringUtils;
import org.jd.util.ExceptionUtil;
import org.jd.util.ImageUtil;
import org.jd.util.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetPermission;
import java.net.NetworkInterface;
import java.nio.channels.SocketChannel;
import java.sql.Date;
import java.time.*;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Created by cuijiandong on 2018/1/17.
 */
public class A {
    public String a = "aaa";

    public static void main(String[] aaa) throws IOException {
        LocalDate ld6_20 = LocalDate.of(2018, 5, 30);
        System.out.println(LocalDate.now().isAfter(ld6_20));
        String[] split = StringUtils.split("", ";");
        System.out.print("sss");

    }

}

