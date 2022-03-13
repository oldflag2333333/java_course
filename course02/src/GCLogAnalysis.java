import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author oldFlag
 * @since 2022/3/10
 */
public class GCLogAnalysis {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        long startMillis = System.currentTimeMillis();
        long timeoutMillis = TimeUnit.SECONDS.toMillis(5);
        long endMillis = startMillis + timeoutMillis;

        LongAdder counter = new LongAdder();

        System.out.println("正在执行...");

        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];

        while (System.currentTimeMillis() < endMillis) {
            Object garbage = generateGarbage(100 * 1024);
            counter.increment();
            int randomIndex = RANDOM.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }

        System.out.println("执行结束！共生成对象次数：" + counter.longValue());

    }

    public static Object generateGarbage(int max) {
        int randomSize = RANDOM.nextInt(max);
        int type = randomSize % 4;

        switch (type) {
            case 0:
                return new int[randomSize];
            case 1:
                return new byte[randomSize];
            case 2:
                return new double[randomSize];
            default:
                StringBuilder sb = new StringBuilder();
                String randomString = "randomString-Anything";
                while (sb.length() < randomSize) {
                    sb.append(randomString).append(max).append(randomSize);
                }
                return sb.toString();
        }
    }


}
