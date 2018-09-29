package org.jd.proxyKeepAlive.http;

/**
 * 异或加密、解密器
 * Created by cuijiandong on 2018/9/3.
 */
public class XORCodec extends Codec {
    public XORCodec(byte[] pswd) {
        super(buffer -> {//加密器
            byte[] bt = buffer.array();
            for (int i = buffer.position(), limit = buffer.limit(); i < limit; i++) {
                byte b = bt[i];
                for (int j = 0; j < pswd.length; j++) {
                    b ^= pswd[j];
                }
                bt[i] = b;
            }
            return buffer;
        }, buffer -> {//解密器
            byte[] bt = buffer.array();
            for (int i = buffer.position(), limit = buffer.limit(); i < limit; i++) {
                byte b = bt[i];
                for (int j = pswd.length - 1; j >= 0; j--) {
                    b ^= pswd[j];
                }
                bt[i] = b;
            }
            return buffer;
        });
    }
}
