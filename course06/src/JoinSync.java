/**
 * @author oldFlag
 * @since 2022/3/25
 */
public class JoinSync {

    private static String str;

    public static void main(String[] args) throws InterruptedException {
        Runnable function = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            str = "join";
        };

        Thread t = new Thread(function);

        t.start();
        t.join();
        System.out.println(str);
    }

}
