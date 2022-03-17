package filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public class SpikeHttpRequestFilter implements HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest request, ChannelHandlerContext ctx) {
        if (request.uri().contains("cowboy")) {
            request.headers().set("spike", "yes");
        }
    }

}
