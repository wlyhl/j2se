package org.jd.j2se.test0.itext;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cuijiandong on 2018/7/10.
 */
public class A {
    public static void main(String[] a) throws Exception {
        String doc = read("d://山东金交所用户注册API商户接入接口文档1.0.0.1.pdf");
        System.out.println(doc);
        System.out.println("=====================================");

        ArrayList<HashMap<String, Object>> list = analyze(doc);
        for (HashMap<String, Object> map : list) {
            out("d://outG//Api" + map.get("codeNum") + ".java", map);
        }

    }

    static ArrayList<HashMap<String, Object>> analyze(String s) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        List<List<String>> group = group(s, "(2\\.3\\.\\d+.+?)(?=2\\.3\\.\\d)", Pattern.DOTALL);
        for(List<String> l :group){
            HashMap<String, Object> map = analyzeBody(l.get(1));
            if (!map.isEmpty())
                list.add(map);
        }
        return list;
    }

    static List<List<String>> group(String source, String reg, int flag) {
        List<List<String>> list = new ArrayList<>();
        try{
            Pattern p = Pattern.compile(reg, flag);
            Matcher m = p.matcher(source);
            while (m.find()) {
                List<String> l = new ArrayList<>(m.groupCount());
                list.add(l);
                for (int i = 0; i <= m.groupCount(); i++)
                    l.add(m.group(i).trim());
            }
        }catch (StackOverflowError e){
            System.err.println("StackOverflowError");
            System.err.println(reg);
            System.out.println(source);
        }
        return list;
    }

    static List<List<String>> group(String sourse, String reg) {
        return group(sourse, reg, 0);
    }

    static HashMap<String, Object> analyzeBody(String body) {
        HashMap<String, Object> map = new HashMap<>();

        //           1 code        2 name                      3 testUrl                             4 prodUrl                            resp
        String reg = "(2\\.3\\.\\d+)(.+)(?:.|\\n)*正式环境地址.*\\n(.*)\\n(?:.|\\n)*解密后.*报文.*\\n((?:.|\\n)*)";
//        Pattern p = Pattern.compile(reg);
//        Matcher m = p.matcher(body);
        List<List<String>> group1 = group(body, reg);
        if(group1.isEmpty())
            return map;
        List<String> group = group1.get(0);
        map.put("code", group.get(1));
        map.put("name", group.get(2));
        map.put("prodUrl", group.get(3));
        map.put("resp", group.get(4));

        String code = map.get("code").toString();
        map.put("codeNum", code.substring(4));

        String resp = map.get("resp").toString();
        List<String> l = group(resp, "\"code\"(?:.|\\n)*?\"data\"((.|\\n)*)?message\"").get(0);
        List<Field> fields = new ArrayList<>();
        if (l.size() > 0) {
            String ss = l.get(1);
            List<List<String>> groups = group(ss, "\"([a-zA-Z0-9]+)\":.*?//((.|\\n)*?)(?=(\"|}))");
            if (groups.size() > 0)
                for (List<String> f : groups)
                    fields.add(new Field(f.get(1), f.get(2)));
        }
        map.put("fields",fields);

        String uri = map.get("prodUrl").toString()
        .replaceAll(" ","").replace("https://www.sdfae.com /ts-web/sdfae/api/","");
        map.put("uri",uri);

        return map;
    }

    static String read(String path) throws IOException {
        PdfReader reader = new PdfReader(path);
        PdfReaderContentParser p = new PdfReaderContentParser(reader);
        int count = reader.getNumberOfPages();
        SimpleTextExtractionStrategy l = new SimpleTextExtractionStrategy();
        for (int i = 1; i <= count; i++) {
            p.processContent(i, l);
        }
        String resultantText = l.getResultantText();
        System.out.println("读取完毕，共"+ resultantText +"字节");
        return resultantText;
    }

    static void out(String path, HashMap<String, Object> param) throws IOException {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        // 获取模板文件
        Template t = ve.getTemplate("vm/Response.vm");
        // 设置变量
        VelocityContext ctx = new VelocityContext();

        for (Map.Entry<String, Object> e : param.entrySet())
            ctx.put(e.getKey(), e.getValue());
        // 输出
        FileWriter fw = new FileWriter(path);
        t.merge(ctx, fw);
        fw.close();
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
//        System.out.println("生成结果：");
//        System.out.println(sw.toString());

    }
}

