package org.jd.hands.free;

import javafx.scene.control.Button;
import org.jd.util.FileUtil;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class CommandButton extends Button {

    public static final String MOUSE_MOVE = "mouseMove";
    public static final String MOUSE_BACK = "mouseBack";
    public static final String MOUSE_CLICK = "mouseClick";
    public static final String PASTE = "paste";
    public static final String DELAY = "delay";
    public static final String EXE = "exe";
    public static final String TYPE = "type";
    //当前按钮按下后需要执行的一串命令
    public final ArrayList<String[]> cmds = new ArrayList<>();
    private static final Robot r = new Robot();

    public CommandButton(String text) {
        super(text);
        setOnMouseClicked((event) -> {
            try {
                for (String[] s : cmds) {
                    switch (s[0]) {
                        case MOUSE_MOVE:
                            if (s.length == 3)
                                r.mouseMove(Integer.valueOf(s[1]), Integer.valueOf(s[2]));
                            else if (s.length == 4) {
                                InputStream in = FileUtil.find(s[1]);
                                if (in == null)
                                    System.out.println("目标图片不存在" + s[1]);
                                r.mouseMove(ImageIO.read(in), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
                            }
                            break;
                        case MOUSE_BACK:
                            r.mouseBack(Integer.parseInt(s[1]));
                            break;
                        case MOUSE_CLICK:
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
                            r.type(s[1]);
                            break;
                        default:
                            System.out.println("命令无效" + s[0]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static CommandButton[] loadFrom(InputStream in) throws UnsupportedEncodingException {
        BufferedReader r = new BufferedReader(new InputStreamReader(in,"utf-8"));
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
                    int spaceIndex=s.indexOf(" ");
                    String cmd = spaceIndex>-1?s.substring(0,spaceIndex ):s;
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
