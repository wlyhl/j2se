package org.jd.proxyKeepAlive.http;

import java.nio.ByteBuffer;
import java.util.function.Function;

/**
 * Created by cuijiandong on 2018/9/3.
 * 加解密
 */
public abstract class Codec {
    public final Function<ByteBuffer, ByteBuffer> encryptor ;
    public final Function<ByteBuffer, ByteBuffer> decryptor ;

    public Codec(Function<ByteBuffer, ByteBuffer> encryptor, Function<ByteBuffer, ByteBuffer> decryptor) {
        this.encryptor = encryptor;
        this.decryptor = decryptor;
    }
}
