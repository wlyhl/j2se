package org.jd.proxy.http;

import java.nio.ByteBuffer;
import java.util.function.Function;

/**
 * Created by cuijiandong on 2018/9/3.
 * 加解密
 */
public abstract class EnDecryptor {
    public final Function<ByteBuffer, ByteBuffer> encryptor ;
    public final Function<ByteBuffer, ByteBuffer> decryptor ;

    public EnDecryptor(Function<ByteBuffer, ByteBuffer> encryptor, Function<ByteBuffer, ByteBuffer> decryptor) {
        this.encryptor = encryptor;
        this.decryptor = decryptor;
    }
}
