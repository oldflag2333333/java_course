import javax.swing.*;
import java.util.concurrent.ExecutionException;

/**
 * @author oldFlag
 * @since 2022/3/27
 */
public class SwingWorkerSync extends SwingWorker<String, Object> {

    @Override
    protected String doInBackground() throws Exception {
        return Thread.currentThread().getName() + " swing swing";
    }

    public static void main(String[] args) {
        SwingWorkerSync task = new SwingWorkerSync();
        task.execute();
        try {
            String result = task.get();
            System.out.println(Thread.currentThread().getName() + " " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
