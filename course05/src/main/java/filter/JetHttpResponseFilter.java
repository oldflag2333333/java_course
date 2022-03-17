package filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public class JetHttpResponseFilter implements HttpResponseFilter {

    @Override
    public void filter(FullHttpResponse response) {
        if (!response.headers().contains("spike")) {
            response.headers().set("jet", "no");
        }
    }

}
