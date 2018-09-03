package org.jd.hands.free;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.jd.util.ExceptionUtil;
import org.jd.util.FileUtil;
import org.jd.util.Logger;
import org.jd.util.Robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cuijiandong on 2018/1/30.
 */
public class HandsFreeController {
    VBox root;
    String configPath;
    public void openWorkPath() {
        Robot.exec("explorer \"" + new File(".").getAbsolutePath() + "\"");
    }

    public void saveConfig() {
        File backUp = new File("configBackup/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".conf");
        FileUtil.copy(new File(configPath), backUp);
        Logger.l.log("备份旧配置文件",backUp.getAbsolutePath());
        try (OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(configPath), Charset.forName("utf-8"))) {
            for(String define:Command.define.keySet()){
                w.write("define ");
                for(Command c:Command.define.get(define))
                    w.write("  "+c+"\n");
            }
            for (Node node : root.getChildren()) {
                if (node instanceof CommandButton) {
                    CommandButton b = (CommandButton) node;
                    w.write(b.getText()+"\n");
                    for (Command c : b.commands)
                        w.write("  "+c+"\n");
                }
            }
            Logger.l.log("保存配置文件到",new File(configPath).getAbsolutePath());
        } catch (Exception e) {
            ExceptionUtil.thro(e);
        }
    }
}
