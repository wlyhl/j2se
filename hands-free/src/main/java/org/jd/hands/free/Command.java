package org.jd.hands.free;

import org.jd.image.find.Position;
import org.jd.image.find.TargetImg;
import org.jd.image.find.sample.AverageSample;
import org.jd.util.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by cuijiandong on 2018/1/22.
 */
public class Command implements CMD {
    private static final Logger log = new Logger(System.out);
    private static final Robot r = new Robot();
    private String cmd;
    private String[] param;
    public static HashMap<String, ArrayList<Command>> define;

    public Command(String conf) {//type tab
        String[] c = conf.split(" ");
        cmd = c[0];
        ArrayList<String> param = new ArrayList<>();
        for (int i = 1; i < c.length; i++)
            param.add(escape(c[i]));
        this.param = param.toArray(new String[c.length - 1]);
    }

    /**
     * 转义
     */
    private String escape(String s) {
        s = s.replace( "\\s", " ");
        s = s.replace("\\\\", "\\");
        return s;
    }

    /**
     * 执行命令
     *
     * @param p 参数
     * @return 是否执行成功
     * @throws Exception
     */
    public boolean exe(String[] p) throws Exception {
        log.log("-->", cmd, Arrays.toString(p));
        switch (cmd) {
            case PASTE:
                r.paste(p[0]);
                break;
            case EXE:
                StringBuilder sb = new StringBuilder();
                for (String s : p)
                    sb.append(s).append(" ");
                sb.setLength(sb.length() - 1);
                Runtime.getRuntime().exec(sb.toString());
                break;
            case MOUSE_MOVE:
                if (p.length == 2) //mouseMove 100 100
                    r.mouseMove(Integer.valueOf(p[0]), Integer.valueOf(p[1]));
                else {//找到图片坐标并移动鼠标至图片附近
                    InputStream in = FileUtil.find(p[0]);
                    log.log("加载目标图片=== ", p[0]);
                    if (in == null)
                        throw new RuntimeException("加载目标图片失败，未找到目标图片！");
                    BufferedImage img = ImageIO.read(in);
                    TargetImg targetImg = new TargetImg(img, 15, new AverageSample(30, img.getWidth(), img.getHeight()));
                    Position pos = null;
                    Timer t = new Timer();
                    log.log("在屏幕上搜索目标图片");
                    for (int i = 0; i < 5 && (pos = targetImg.findIn(r.screencap())) == null; i++) {
                        log.log("第", i, "次搜索未找到，1.5秒后继续搜索。本次耗时", t.cost());
                        r.delay(1500);
                    }
                    if (pos != null) {
                        log.log("找到目标图片，坐标", pos, "耗时" + t.cost());
                        if (p.length == 3) {//mouseMove d:\img\0.png +10 -20
                            pos.x += Integer.parseInt(p[1]);
                            pos.y += Integer.parseInt(p[2]);
                        }
                        r.mouseMove(pos.x, pos.y);
                    } else throw new RuntimeException("图片坐标未找到");
                }
                break;
            case MOUSE_BACK:
                r.mouseBack(Integer.parseInt(p[0]));
                break;
            case MOUSE_CLICK:
                r.mouseClick();
                if (p.length>0)
                    for (int i = Integer.valueOf(p[0]); i > 1; i--)
                        r.mouseClick();
                break;
            case DELAY:
                r.delay(Integer.valueOf(p[0]));
                break;
            case TYPE:
                for (int i = 0; i < p.length; i++)
                    r.type(p[i]);
                break;
            case TYPE_WITH:
                r.typeWith(p[0], p[1]);
                break;
            default:
                ArrayList<Command> commands = define.get(cmd);
                if (commands == null) {
                    log.log("命令无效", cmd);
                    return false;
                }
                Command name = commands.get(0);//clickAndBack x y
                HashMap<String, String> params = new HashMap<>();
                for (int i = 0; i < name.param.length; i++) {
                    params.put(name.param[i], p[i]);
                }
                for (int i = 1; i < commands.size(); i++) {
                    Command cmd = commands.get(i);//mouseMove x y
                    String[] param = Arrays.copyOf(cmd.param, cmd.param.length);
                    for (int j = 0; j < param.length; j++) {
                        String value = params.get(param[j]);
                        if (value != null)
                            param[j] = value;
                    }
                    cmd.exe(param);
                }
        }
        return true;
    }

    public boolean exe() throws Exception {
        return exe(param);
    }

    /**
     * 定义一组命令
     * define clickAndBack x y
     * mouseMove x y
     * mouseClick
     * mouseBack 1
     *
     * @param name 命令组签名 clickAndBack x y
     */
    public static ArrayList<Command> define(String name) {
        if (define == null)
            define = new HashMap<>();
        Command cName = new Command(name);
        if (define.get(cName.cmd) != null)
            throw new RuntimeException(cName + "已经定义过");
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(cName);
        define.put(cName.cmd, commands);
        return commands;
    }
}

interface CMD {
    String MOUSE_MOVE = "mouseMove";
    String MOUSE_BACK = "mouseBack";
    String MOUSE_CLICK = "mouseClick";
    String PASTE = "paste";
    String DELAY = "delay";
    String EXE = "exe";
    String TYPE = "type";
    String TYPE_WITH = "typeWith";
}
