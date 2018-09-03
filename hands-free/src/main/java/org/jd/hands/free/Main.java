package org.jd.hands.free;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jd.util.FileUtil;
import org.jd.util.Logger;
import org.jd.util.Robot;

import java.io.File;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage s) throws Exception {
        List<String> param = getParameters().getRaw();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hands-free.fxml"));
        VBox root = loader.load();
        HandsFreeController c = loader.getController();
        c.configPath=param.size()>0?param.get(0):"hands-free.conf";
        c.root=root;

        root.getChildren().addAll(CommandButton.loadFrom(FileUtil.find(c.configPath)));
        Scene scene = new Scene(root, 200, -1);

        s.setTitle("呵");
        s.getIcons().add(new Image(FileUtil.find("icon.png")));
        s.setAlwaysOnTop(true);
        s.setScene(scene);
        s.show();

        s.setX(Robot.screenRectangle.width - s.getWidth());
    }

    public static void main(String[] args) {
//        String[] a = {"/D:\\program\\java\\hands-free\\hands-free-min.conf"};
        if("file".equals(Main.class.getResource("Main.class").getProtocol()))
            System.setProperty("user.dir","D:\\program\\java\\hands-free\\");
        launch(args);
    }
}
