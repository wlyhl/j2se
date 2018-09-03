package org.jd.regex;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main extends Application {

    public TextArea string;
    public TextArea regex;
    public CheckBox dotAll;
    public CheckBox positive;
    public Pane result;//结果容器


    public void match() {
        Platform.runLater(() -> {
            long l=new Date().getTime();
            try{
                if (StringUtils.isBlank(regex.getText()) || StringUtils.isBlank(string.getText()))
                    return;
                result.getChildren().clear();

                Pattern p = Pattern.compile(regex.getText(), dotAll.selectedProperty().getValue() ? Pattern.DOTALL : 0);
                Matcher m = p.matcher(string.getText());
                while (m.find()) {
                    VBox groupBox=new VBox();
                    result.getChildren().add(new HBox(new TextArea(m.group(0)),groupBox));
                    ObservableList<Node> children = groupBox.getChildren();
                    for (int i = 1; i <= m.groupCount(); i++)
                        children.add(new TextArea( m.group(i)));
                }
                result.getChildren().add(new Text("耗时："+(new Date().getTime()-l)));
            }catch (Error e){
                e.printStackTrace();
                result.getChildren().add(new Text("异常耗时："+(new Date().getTime()-l)));
            }
        });
    }

    @Override
    public void start(Stage s) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        s.setTitle("Java正则测试");
        s.setScene(scene);
        s.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void regPressed(KeyEvent keyEvent) {
        if (KeyCode.ENTER == keyEvent.getCode()) {
            keyEvent.consume();
        }
    }

    public void regReleased(KeyEvent keyEvent) {
        if (KeyCode.ENTER == keyEvent.getCode()) {
            match();
            keyEvent.consume();
        } else if (positive.selectedProperty().getValue())
            match();
    }
}
