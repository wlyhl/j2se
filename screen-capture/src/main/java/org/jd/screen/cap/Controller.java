package org.jd.screen.cap;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by cuijiandong on 2018/1/30.
 */
public class Controller {
    private Stage rect;//截屏范围

    public void showRect(ActionEvent actionEvent) {
        if (rect == null) {
            rect = new Stage();
            rect.initStyle(StageStyle.TRANSPARENT);
            rect.setAlwaysOnTop(true);

            Pane p=new Pane();



            rect.setScene(new Scene(p));

        }
        rect.show();
    }

    public void hideRect(ActionEvent actionEvent) {
        if (rect != null)
            rect.hide();
    }

    public void setDirectory(ActionEvent actionEvent) {
    }
}
