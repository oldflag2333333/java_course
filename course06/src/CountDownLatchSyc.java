import java.util.concurrent.CountDownLatch;

/**
 * @author oldFlag
 * @since 2022/3/25
 */
public class CountDownLatchSyc {

    private static String str;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable function = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            str = "count down latch";
            countDownLatch.countDown();
        };

        Thread t = new Thread(function);
        t.start();
        countDownLatch.await();
        System.out.println(str);
    }


}
