package org.jd.hands.free;

import javafx.scene.control.Button;
import org.jd.util.ExceptionUtil;
import org.jd.util.Logger;
import org.jd.util.LoopReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class CommandButton extends Button {
    static final Logger log = new Logger(System.out);
    //当前按钮按下后需要执行的一串命令
    public final ArrayList<Command> commands = new ArrayList<>();

    public CommandButton(String text) {
        super(text);
        setOnMouseClicked((event) -> {
            switch (event.getButton()){
                case PRIMARY://开始自动点击
                    log.log("【", text, "】");
                    try {
                        for (Command c : commands)
                            c.exe();
                    } catch (Exception e) {
                        Logger.l.log(e);
                    }
                    break;
                case SECONDARY://配置按钮步骤

            }

        });
    }

    public static CommandButton[] loadFrom(InputStream in) throws UnsupportedEncodingException {
        LoopReader r = new LoopReader(new InputStreamReader(in, "utf-8"));
        ArrayList<CommandButton> list = new ArrayList<>();
        CommandButton that = null;
        try {
            for (String s; (s = r.readLine()) != null; ) {
                if (s.startsWith("//") || "".equals(s.trim()))
                    continue;
                if ("/*".equals(s)) {//多行注释
                    r.readUntil("*/"::equals);
                } else if (s.startsWith("define ")) {
                    ArrayList<Command> define = Command.define(s.substring(7));
                    r.readWhile(s1 -> s1.startsWith("  ") && s1.length() > 2, s1 -> define.add(new Command(s1)));
                } else if (s.startsWith("  ")) {//配置按钮点击后的流程
                    that.commands.add(new Command(s));
                } else {//增加一个新按钮
                    log.log("增加按钮", s);
                    list.add(that = new CommandButton(s));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.toArray(new CommandButton[list.size()]);
    }
    public static void saveConfig(List<CommandButton> buttons,String fileName) throws UnsupportedEncodingException {
        try(OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(fileName), Charset.forName("utf-8"));){
            for(CommandButton b:buttons){
                w.write(b.getText());
                w.write("\n");
                for(Command c:b.commands){
                    w.write("  ");
                    w.write(c.toString());
                    w.write("\n");
                }
            }
        } catch (Exception e) {
            ExceptionUtil.thro(e);
        }
    }
}
