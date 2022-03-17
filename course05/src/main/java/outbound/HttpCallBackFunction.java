package outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.http.HttpResponse;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
@FunctionalInterface
public interface HttpCallBackFunction {

    void execute(HttpResponse httpResponse);

}
