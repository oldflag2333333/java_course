import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author oldFlag
 * @since 2022/3/26
 */
public class FutureSync {

    private static ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {

        Future<String> future = executor.submit(() -> "future");

        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }

}
