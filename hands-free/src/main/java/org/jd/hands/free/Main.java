package org.jd.hands.free;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Robot r=new Robot();
        Rectangle2D b = Screen.getPrimary().getBounds();

        BufferedImage screen = r.createScreenCapture(new Rectangle(0, 0, (int)b.getWidth(), (int)b.getHeight()));

        Button btn = new Button();
        btn.setText("");
        btn.setOnAction((event)-> {
            System.out.println("解放双手");
        });

        VBox root = new VBox(2);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));
        root.getChildren().add(btn);
        root.getChildren().add(new Button("asdasd"));
        Scene scene =new Scene(root, 200,-1);

        primaryStage.setTitle("解放双手");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
