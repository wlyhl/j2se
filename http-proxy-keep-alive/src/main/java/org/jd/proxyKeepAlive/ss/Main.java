package org.jd.proxyKeepAlive.ss;

import org.jd.proxyKeepAlive.http.ProxyClient;
import org.jd.proxyKeepAlive.http.ProxyServer;

public class Main {

    /**
     * server: 监听 2001 端口，解密密码 abc
     * 2001 abc
     * client: 监听 2000 端口，解密密码 abc ，远程服务器地址 127.0.0.1:2001
     * 2000 abc 127.0.0.1 2001
     */
    public static void main(String[] a) throws Exception {
        if (a.length == 2)
            new ProxyServer().start(a);
        else
            new ProxyClient().start(a);
    }
}
