package org.jd.port.scanner;

import java.util.Arrays;

/**
 * Created by cuijiandong on 2018/1/5.
 */
public class IpIterator {
    public static void iterate(String from,String to,IpIterateCallback callback){
        int[] f=ip2int(from);
        int[] fSource= Arrays.copyOf(f,f.length);
        int[] t=ip2int(to);
        for(int i=f.length-1;;){
            while(f[i]<=t[i]){
                callback.call(int2ip(f));
                f[i]++;
            }
            while(--i>-1 && f[i]>=t[i]);
            if(i==-1)  break;
            f[i]++;
            while(++i<f.length)
                f[i]=fSource[i];
            i--;
        }
    }
    private static int[] ip2int(String ip){
        String[] s=ip.split("\\.");
        int[] i=new int[s.length];
        for(int x=0;x<s.length;x++)
            i[x]=Integer.valueOf(s[x]);
        return i;
    }
    private static String int2ip(int[] ip){
        StringBuilder sb=new StringBuilder();
        for(int i:ip)
            sb.append(i).append(".");
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
}
