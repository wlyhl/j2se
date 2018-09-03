package org.jd.image.find;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.jd.image.find.sample.AverageSample;
import org.jd.image.find.sampleFilter.ColorFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by cuijiandong on 2018/3/16.
 * 测试取样器和过滤器
 */
public class Test extends Application {
    public static void main(String[] a) throws Exception {

        BufferedImage img = ImageIO.read(new File("G:\\git\\j2se\\test-0\\src\\main\\resources\\hashWorld\\1.png"));

        Thread.sleep(2000);
        g.setFill(new ImagePattern(SwingFXUtils.toFXImage(img, null)));
        g.fillRect(0, 0, img.getWidth(), img.getHeight());

        testSample(img, AverageSample.count(20));
        launch(a);
    }

    static void testSample(BufferedImage img, Sample sample) {
        List<Position> pos = sample.sample(img.getWidth(), img.getHeight());
        g.setStroke(Color.BLUE);
        g.setLineWidth(3);
        for (Position p : pos) {
            g.strokeLine(p.x, p.y, p.x, p.y);
        }
        testFilter(img,pos,new ColorFilter(java.awt.Color.WHITE));
    }

    static void testFilter(BufferedImage img, List<Position> pos, SampleFilter f) {
        List<Position> fpos = f.filt(pos, img);
        g.setStroke(Color.RED);
        g.setLineWidth(3);
        for (Position p : fpos) {
            g.strokeLine(p.x, p.y, p.x, p.y);
        }
    }

    static Canvas canvas = new Canvas(400, 400);
    static GraphicsContext g = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage s) throws Exception {
        s.setTitle("测试");
        s.setAlwaysOnTop(true);
//        s.setScene(new Scene(new Pane(canvas)));
        Group root = new Group();
        root.getChildren().add(canvas);
        s.setScene(new Scene(root, 400, 400));
        s.show();
    }
}
