/**
 * @author oldFlag
 * @since 2022/3/25
 */
public class SynchronizedLockSync {

    private static String str;


    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Runnable function = () -> {
            try {
                Thread.sleep(1000);
                synchronized (lock) {
                    str = "lock";
                    lock.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t = new Thread(function);
        t.start();

        synchronized (lock) {
            lock.wait();
        }
        System.out.println(str);
    }
}
