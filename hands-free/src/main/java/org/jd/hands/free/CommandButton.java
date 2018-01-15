package org.jd.hands.free;

import javafx.scene.control.Button;
import org.jd.util.FileUtil;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class CommandButton extends Button {

    public static final String MOUSE_MOVE = "mouseMove";
    public static final String MOUSE_CLICK = "mouseClick";
    public static final String PASTE = "paste";
    public static final String DELAY = "delay";
    public static final String EXE = "exe";
    public static final String TYPE = "type";
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
                                    System.out.println("目标图片未找到" + s[1]);
                                r.mouseMove(ImageIO.read(in), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
                            }
                            break;
                        case MOUSE_CLICK:
                            r.mouseClick();
                            break;
                        case PASTE:
                            StringBuilder sb = new StringBuilder();
                            for (int i = 1; i < s.length; i++)
                                sb.append(s[i]).append(" ");
                            r.paste(sb.toString());
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

    public static CommandButton[] loadFrom(InputStream in) {

        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        ArrayList<CommandButton> list = new ArrayList<>();
        CommandButton that = null;
        try {
            boolean note = false;
            for (String s; (s = r.readLine()) != null; ) {
                if (s.startsWith("//") || "".equals(s.trim()))
                    continue;
                if (note) {//在多行注释中
                    note = !s.startsWith("*/");//注释未结束
                    continue;
                } else {
                    if (note = s.startsWith("/*"))
                        continue;
                }

                if (s.startsWith("  ")) {
                    s = s.substring(2);
                    if (s.startsWith(PASTE)) {
                        String[] cmd = {PASTE, s.substring(PASTE.length() + 1)};
                        that.cmds.add(cmd);
                    } else if (s.startsWith(EXE)) {//exe chrome.exe http://baidu.com
                        String[] cmd = {EXE, s.substring(EXE.length() + 1)};
                        that.cmds.add(cmd);
                    } else {
                        that.cmds.add(s.split(" "));
                    }
                } else {
                    list.add(that = new CommandButton(s));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new CommandButton[list.size()]);
    }
}
