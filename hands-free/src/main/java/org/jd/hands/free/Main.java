package org.jd.hands.free;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jd.util.FileUtil;
import org.jd.util.Robot;

import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage s) throws Exception {
        List<String> param = getParameters().getRaw();
        VBox root = new VBox(2);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(CommandButton.loadFrom(FileUtil.find(param.isEmpty() ? "hands-free.conf" : param.get(0))));
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
        launch(args);
    }
}
