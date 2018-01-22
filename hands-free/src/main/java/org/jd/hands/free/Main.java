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
    public void start(Stage primaryStage) throws Exception {
        List<String> param = super.getParameters().getRaw();
        VBox root = new VBox(2);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(CommandButton.loadFrom(FileUtil.find(param.isEmpty() ? "hands-free.conf" : param.get(0))));
        Scene scene = new Scene(root, 200, -1);

        primaryStage.setTitle("呵");
        primaryStage.getIcons().add(new Image(FileUtil.find("icon.png")));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setX(Robot.screenRectangle.width - primaryStage.getWidth());
    }

    public static void main(String[] args) {
//        String[] a = {"/D:\\program\\java\\hands-free\\hands-free-min.conf"};
        launch(args);
    }
}
