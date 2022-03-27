/**
 * @author oldFlag
 * @since 2022/3/25
 */
public class VolatileSync {

    private static volatile String str;

    public static void main(String[] args) {
        Runnable function = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            str = "volatile";
        };

        Thread t = new Thread(function);
        t.start();
        while (str == null) {
            // spin
        }
        System.out.println(str);
    }

}
