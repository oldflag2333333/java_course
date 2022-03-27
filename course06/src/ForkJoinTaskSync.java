import java.util.concurrent.RecursiveTask;

/**
 * @author oldFlag
 * @since 2022/3/27
 */
public class ForkJoinTaskSync extends RecursiveTask<String> {

    @Override
    protected String compute() {
        return "fork join task";
    }

    public static void main(String[] args) {
        ForkJoinTaskSync forkJoinTaskSync = new ForkJoinTaskSync();
        forkJoinTaskSync.fork();
        String result = forkJoinTaskSync.join();

        System.out.println(result);
    }
}
