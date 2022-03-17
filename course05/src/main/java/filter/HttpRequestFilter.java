package filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public interface HttpRequestFilter {

    void filter(FullHttpRequest request, ChannelHandlerContext ctx);

}
