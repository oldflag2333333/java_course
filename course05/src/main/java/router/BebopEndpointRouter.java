package router;

import com.google.common.collect.Maps;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public class BebopEndpointRouter implements HttpEndpointRouter {

    private static Map<String, String> ENDPOINT_MAP = Maps.newHashMap();

    static {
        ENDPOINT_MAP.put("spike", "http://localhost:8801");
        ENDPOINT_MAP.put("faye", "http://localhost:8802");
        ENDPOINT_MAP.put("jet", "http://localhost:8803");
    }

    @Override
    public String route(FullHttpRequest httpRequest) {
        if (httpRequest.uri().contains("spike")) {
            return ENDPOINT_MAP.get("spike");
        } else if (httpRequest.uri().contains("faye")) {
            return ENDPOINT_MAP.get("faye");
        } else {
            return ENDPOINT_MAP.get("jet");
        }
    }
}
