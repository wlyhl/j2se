package org.jd.hands.free;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jd.util.FileUtil;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(2);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));
        root.getChildren().addAll(CommandButton.loadFrom(FileUtil.find("conf.cfg")));
        Scene scene =new Scene(root, 200,-1);

        primaryStage.setTitle("");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }
}
