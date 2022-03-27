import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author oldFlag
 * @since 2022/3/25
 */
public class ReentrantLockSync {

    private static String str;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Runnable function = () -> {
            try {
                lock.lock();
                Thread.sleep(1000);

                str = "reentrant lock";
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };

        Thread t = new Thread(function);
        t.start();

        try {
            lock.lock();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        System.out.println(str);
    }
}
