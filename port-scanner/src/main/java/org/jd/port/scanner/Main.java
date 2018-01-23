package org.jd.port.scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by cuijiandong on 2018/1/5.
 */
public class Main extends Application {

    @Override
    public void start(Stage s) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/port-scanner.fxml"));
        VBox root = loader.load();
        PortScannerController controller = loader.getController();
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String[] ips = ip.split("\\.");
            controller.ip0.setText(ips[0]);
            controller.ip1.setText(ips[1]);
            controller.ip2.setText(ips[2]);
            controller.ip3.setText("1-255");
            controller.log("本机ip:"+ip);
        } catch (UnknownHostException e) {
            controller.log(e.getMessage());
        }

        s.setScene(new Scene(root));
        s.setTitle("端口扫描");
        s.setWidth(300);
        s.show();
        s.setOnCloseRequest(e->System.exit(0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
