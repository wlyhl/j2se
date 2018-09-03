package org.jd.screen.cap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/root.fxml"));
        VBox root = loader.load();
//        Controller c = loader.getController();
        Scene scene = new Scene(root, 200, -1);

        s.setTitle("截屏");
        s.setScene(scene);
        s.show();

        s.setX(Robot.screenRectangle.width - s.getWidth());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
