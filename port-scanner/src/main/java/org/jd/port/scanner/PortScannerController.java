package org.jd.port.scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import org.jd.util.Assert;

/**
 * Created by cuijiandong on 2018/1/23.
 */
public class PortScannerController {
    public CheckBox showDetail;
    @FXML
    TextField ip0;
    @FXML
    TextField ip1;
    @FXML
    TextField ip2;
    @FXML
    TextField ip3;
    @FXML
    TextField port;
    @FXML
    TextField threadNum;
    @FXML
    TextField timeOut;
    @FXML
    TextArea log;

    synchronized void log(Object... o) {
        Platform.runLater(() -> {
            for (Object ob : o)
                log.appendText(ob.toString());
            log.appendText("\n");
        });
    }

    private volatile Scanner scanner;

    @FXML
    void scan() {
        if (scanner != null)
            return;
        new Thread(() -> {
            try {
                Scanner.timeOut = Assert.isInt(timeOut.getText(), "输入超时时间");
                scanner = new Scanner(Assert.isInt(threadNum.getText(), "输入线程数"), addr -> log(addr.toString() + " success"),
                        addr -> {
                            if (showDetail.isSelected()) log(addr.toString());
                        });
                int[] i = IpIterator.convertRange(Assert.ifNotEmpty(port.getText().trim(), "未输入端口号"));
                log("扫描中...");
                while (i[0] <= i[1]) {
                    IpIterator.iterate(getIp(ip0, ip1, ip2, ip3), ip -> scanner.scan(ip, i[0]));
                    i[0]++;
                }
                log("扫描结束");
                scanner.interruptAllJob();
                scanner = null;
            } catch (Exception e) {
                log(e.getMessage());
            }
        }).start();
    }

    private String getIp(TextField... txt) {
        StringBuilder sb = new StringBuilder();
        for (TextField t : txt) {
            sb.append(Assert.ifNotEmpty(t.getText().trim(), "ip输入不完整")).append(".");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @FXML
    void stop() {
        if (scanner != null)
            scanner.interruptAllJob();
        scanner = null;
        log("扫描结束");
    }

    public void clear() {
        log.setText("");
    }


    public void ip0KeyReleased(KeyEvent keyEvent) {
        String[] ip = ip0.getText().split("\\.");
        if (ip.length > 1) {
            TextField[] txt = {ip0, ip1, ip2, ip3};
            for (int i = 0; i < ip.length; i++) {
                txt[i].setText(ip[i]);
                txt[i].requestFocus();
            }
        } else {
            ipKeyReleased(keyEvent);
        }
    }

    public void ipKeyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case PERIOD:
            case DECIMAL:
            case BACK_SPACE:
                TextField txt = (TextField) keyEvent.getTarget();
                TextField[] txts = {ip0, ip1, ip2, ip3};
                int i = txt.getId().charAt(2) - '0';
                String s = txt.getText();
                switch (keyEvent.getCode()) {
                    case PERIOD:
                    case DECIMAL:
                        if (s.length() > 0 && ++i < txts.length) {
                            txt.setText(s.substring(0,s.length()-1));
                            txts[i].requestFocus();
                        }
                        break;
                    case BACK_SPACE:
                        if (s.length() == 0 && --i >= 0)
                            txts[i].requestFocus();
                }
        }
    }
}
