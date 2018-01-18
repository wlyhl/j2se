package org.jd.hands.free;

import javafx.scene.control.Button;
import org.jd.image.find.Position;
import org.jd.image.find.TargetImg;
import org.jd.image.find.sample.AverageSample;
import org.jd.util.FileUtil;
import org.jd.util.Logger;
import org.jd.util.Robot;
import org.jd.util.Timer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class CommandButton extends Button {
    static final Logger log = new Logger(System.out);
    public static final String MOUSE_MOVE = "mouseMove";
    public static final String MOUSE_BACK = "mouseBack";
    public static final String MOUSE_CLICK = "mouseClick";
    public static final String PASTE = "paste";
    public static final String DELAY = "delay";
    public static final String EXE = "exe";
    public static final String TYPE = "type";
    public static final String TYPE_WITH = "typeWith";
    //当前按钮按下后需要执行的一串命令
    public final ArrayList<String[]> cmds = new ArrayList<>();
    private static final Robot r = new Robot();

    public CommandButton(String text) {
        super(text);
        setOnMouseClicked((event) -> {
            log.log("【",text,"】");
            try {
               loop: for (String[] s : cmds) {
                    log.log("-->",Arrays.toString(s));
                    switch (s[0]) {
                        case MOUSE_MOVE:
                            if (s.length == 3) //mouseMove 100 100
                                r.mouseMove(Integer.valueOf(s[1]), Integer.valueOf(s[2]));
                            else {//找到图片坐标并移动鼠标至图片附近
                                InputStream in = FileUtil.find(s[1]);
                                log.log("加载目标图片=== ", s[1]);
                                if (in == null) {
                                    log.log("加载目标图片失败，未找到目标图片！");
                                    break loop;
                                }
                                BufferedImage img = ImageIO.read(in);
                                TargetImg targetImg = new TargetImg(img, 15, new AverageSample(30,img.getWidth(),img.getHeight()));
                                Position pos = null;
                                Timer t=new Timer();
                                log.log("在屏幕上搜索目标图片");
                                for (int i = 0; i < 5 && (pos = targetImg.findIn(r.screencap())) == null; i++) {
                                    log.log("第",i,"次搜索未找到，1.5秒后继续搜索。本次耗时",t.cost());
                                    r.delay(1500);
                                }
                                if (pos != null) {
                                    log.log("找到目标图片，坐标",pos,"耗时"+t.cost());
                                    if(s.length==4){//mouseMove d:\img\0.png +10 -20
                                        pos.x += Integer.parseInt(s[2]);
                                        pos.y += Integer.parseInt(s[3]);
                                    }
                                    r.mouseMove(pos.x, pos.y);
                                }else break loop;
                            }
                            break;
                        case MOUSE_BACK:
                            r.mouseBack(Integer.parseInt(s[1]));
                            break;
                        case MOUSE_CLICK:
                            r.mouseClick();
                            if(s.length == 2)
                                for(int i=Integer.valueOf(s[1]);i>1;i--)
                                    r.mouseClick();
                            break;
                        case PASTE:
                            r.paste(s[1]);
                            break;
                        case DELAY:
                            r.delay(Integer.valueOf(s[1]));
                            break;
                        case EXE:
                            Runtime.getRuntime().exec(s[1]);
                            break;
                        case TYPE:
                            for(int i=1;i<s.length;i++)
                                r.type(s[i]);
                            break;
                        case TYPE_WITH:
                            r.typeWith(s[1],s[2]);
                            break;
                        default:
                            log.log("命令无效" ,s[0]);
                            break loop;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static CommandButton[] loadFrom(InputStream in) throws UnsupportedEncodingException {
        BufferedReader r = new BufferedReader(new InputStreamReader(in, "utf-8"));
        ArrayList<CommandButton> list = new ArrayList<>();
        CommandButton that = null;
        try {
            boolean note = false;
            for (String s; (s = r.readLine()) != null; ) {
                if (s.startsWith("//") || "".equals(s.trim()))
                    continue;
                if (note) {//在多行注释中
                    note = !"*/".equals(s);//注释未结束
                    continue;
                } else {
                    if (note = s.startsWith("/*"))
                        continue;
                }

                if (s.startsWith("  ")) {//配置按钮点击后的流程
                    s = s.substring(2);
                    int spaceIndex = s.indexOf(" ");
                    String cmd = spaceIndex > -1 ? s.substring(0, spaceIndex) : s;
                    switch (cmd) {
                        case PASTE:
                        case EXE:
                            that.cmds.add(oneParam(cmd, s));
                            break;
                        default:
                            that.cmds.add(s.split(" "));
                    }
                } else {//增加一个新按钮
                    list.add(that = new CommandButton(s));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new CommandButton[list.size()]);
    }

    private static String[] oneParam(String cmd, String conf) {
        String[] result = {cmd, conf.substring(cmd.length() + 1)};
        return result;
    }
}
