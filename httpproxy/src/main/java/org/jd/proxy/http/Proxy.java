package org.jd.proxy.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SocketChannel;

/**
 * Created by cuijiandong on 2018/8/31.
 */
public class Proxy extends Thread {
    Logger log = LoggerFactory.getLogger(getClass());
    SocketChannel accept;
    public static EnDecryptor enDecryptor;
}
