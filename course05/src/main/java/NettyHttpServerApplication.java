/**
 * @author oldFlag
 * @since 2022/3/17
 */
public class NettyHttpServerApplication {

    public static void main(String[] args) throws Exception {
        int port = 8810;
        if (args != null && args.length > 1) {
            port = Integer.parseInt(args[0]);
        }

        new NettyHttpServer(port).run();
    }

}
