package router;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public interface HttpEndpointRouter {

    String route(FullHttpRequest httpRequest);

}
