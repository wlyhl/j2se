package org.jd.image.find.sample;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import com.sun.javafx.robot.FXRobotImage;
import com.sun.javafx.robot.impl.BaseFXRobot;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.awt.Robot;
import java.util.ArrayList;

/**
 * Created by cuijiandong on 2018/1/11.
 */
public class CommandButton extends Button {
    static final int delay=1;
    public final ArrayList<String[]> cmds=new ArrayList<>();
    private final Robot r;
    public CommandButton(String text) throws Exception {
        super(text);
        FXRobotImage.
        r=new Robot();
        setOnMouseClicked((event)->{
            event.getButton()
            try{
                for(String[] s:cmds){
                    switch (s[0]){
                        case "mouseMove":
                            r.mouseMove(Integer.valueOf(s[1]),Integer.valueOf(s[2]));
                            break;
                        case "mouseClick":
                            r.mousePress(javafx.scene.input.MouseButton.);
                            Thread.sleep(delay);
                            r.mouseRelease();
                            break;
                        case "keyDown":
                            break;
                        case "":
                            break;
                        case "":
                            break;

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
class CommandButtonClick implements EventHandler<javafx.scene.input.MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

    }
}
