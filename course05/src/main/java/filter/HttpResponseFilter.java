package filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public interface HttpResponseFilter {

    void filter(FullHttpResponse response);

}
