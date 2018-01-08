package org.jd.wx.jump.shell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.FutureTask;

/**
 * Created by cuijiandong on 2018/1/3.
 */
public class Shell {
    private BufferedWriter out;

    public final Process p;

    public Shell(String shell) throws IOException {
        p = Runtime.getRuntime().exec("cmd");
        out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        new Thread(()->{
            try{
                for(;;) System.err.write(p.getErrorStream().read());
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public InputStream exe(String shell, String ... waitFor){
        return exeAndWait(shell,p.getInputStream(),waitFor);
    }
//    public InputStream exeAndWaitError(String shell,String ... waitFor){
//        return exeAndWait(shell,p.getErrorStream(),waitFor);
//    }
    private InputStream exeAndWait(String shell, InputStream in, String ... waitFor) {
        Wait wait=new Wait(p.getInputStream());
        wait.addWaitString(shell);
        wait.addWaitString(waitFor);
        FutureTask<InputStream> futureTask = new FutureTask<>(wait);
        new Thread(futureTask).start();
        try {
            out.write(shell+"\n");
            out.flush();
            return futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
