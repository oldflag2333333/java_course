import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author oldFlag
 * @since 2022/3/25
 */
public class CyclicBarrierSync {

    private static String str;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Runnable function = () -> {
            try {
                Thread.sleep(1000);
                str = "cyclic barrier";
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };

        Thread t = new Thread(function);
        t.start();
        cyclicBarrier.await();
        System.out.println(str);
        cyclicBarrier.reset();
    }

}
