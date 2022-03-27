import java.util.concurrent.Semaphore;

/**
 * @author oldFlag
 * @since 2022/3/25
 */
public class SemaphoreSync {

    private static String str;

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(2);

        Runnable function = () -> {
            try {
                semaphore.acquire();
                Thread.sleep(1000);
                str = "semaphore";
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        };


        Thread t = new Thread(function);
        t.start();

        semaphore.acquire();
        System.out.println(str);
        semaphore.release();
    }


}
