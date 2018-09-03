package org.jd.j2se.test0.log;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cuijiandong on 2018/8/22.
 */
public class LogFilter {
    public static void main(String[] a) throws Exception {
        step1();
        step2();
        step3();
    }

    static Properties openIds = new Properties();
    static Properties channels = new Properties();

    static void step1() throws Exception {
        step1("D:\\logs2\\wx_2018-08-17-70.log");
        step1("D:\\logs2\\wx_2018-08-17-71.log");
        System.out.println("openId总共" + openIds.size());
        openIds.store(new FileOutputStream("D:\\logs2\\openIds.txt"), "openid");

        System.out.println("channels总共" + channels.size());
        channels.store(new FileOutputStream("D:\\logs2\\channels.txt"), "channels");
    }

    static void step1(String log) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader(log));
        Pattern reg = Pattern.compile("当前session id=(.*)中的openid= (.*)");
        Pattern reg2 = Pattern.compile("获取微信号的openId=(.*)");
        Pattern reg3 = Pattern.compile("www\\.gxjlcf\\.com%2Fwx%2Fpmt\\.html%3Fc%3D(.*)&response_type=code&scope=snsapi_base");
        int i = 1;
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            Matcher m = reg.matcher(line);
            if (m.find()) {
                String sessionId = m.group(1);
                String openId = m.group(2);
                if ("null".equals(openId)) {
                    line = r.readLine();
                    Matcher m2 = reg2.matcher(line);
                    i++;
                    if (m2.find()) {
                        openId = m2.group(1);
                    } else {
                        Matcher m3 = reg3.matcher(line);
                        if (m3.find()) {
                            String c = m3.group(1);
                            channels.put(sessionId, c);
                        }
                    }
                }
                String old = openIds.getProperty(sessionId);
                if (!"null".equals(openId)) {
                    if (old != null && !old.equals(openId))
                        System.out.println(old + "------第" + i + "行------" + openId);
                    openIds.setProperty(sessionId, openId);
                }
            }
            i++;
        }
    }

    static Properties openIdChannel = new Properties();

    static void step2() throws Exception {
        for (Map.Entry<Object, Object> e : channels.entrySet()) {
            String sessionId = e.getKey().toString();
            String openId = openIds.getProperty(sessionId);
            if (openId != null)
                openIdChannel.put(openId, e.getValue());
            else
                System.out.println("sessionId   " + sessionId);
        }
        System.out.println("oc" + openIdChannel.size());
        openIdChannel.store(new FileOutputStream("D:\\logs2\\openId-channel.txt"), "channels");
    }

    static void step3() throws Exception {
        BufferedWriter w=new BufferedWriter(new FileWriter("D:\\logs2\\sql.txt"));
        Properties members = new Properties();
        members.load(new FileInputStream("D:\\logs2\\member.txt"));
        Properties memberChannel = new Properties();
        for (Map.Entry<Object, Object> e : members.entrySet()) {
            String memberId = e.getKey().toString();
            String channel = null;
            String openId = (String) e.getValue();
            if (openId != null && openId != "") {
                channel = openIdChannel.getProperty(openId);
            }
            memberChannel.setProperty(memberId, channel == null ? "" : channel);
            channel=channel==null?"null":"'"+channel+"'";
            w.write("UPDATE member SET `promotionChannel`= "+channel+" WHERE `memberId`='"+memberId+"';\n");
        }
        System.out.println("memberChannel" + memberChannel.size());
        w.flush();
        w.close();
        memberChannel.store(new FileOutputStream("D:\\logs2\\memberChannel.txt"), "memberChannel");
    }
    static void step4(){
        // UPDATE member SET `promotionChannel`='wt' WHERE `memberId`='2c9082c65977b1b2015977ef0082002b';

    }
}
