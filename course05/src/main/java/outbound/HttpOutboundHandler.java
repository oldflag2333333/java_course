package outbound;

import filter.HttpResponseFilter;
import filter.JetHttpResponseFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.http.util.EntityUtils;
import router.BebopEndpointRouter;
import router.HttpEndpointRouter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public class HttpOutboundHandler {

    private final HttpClient4 httpClient;
    private final HttpEndpointRouter httpEndpointRouter;
    private final HttpResponseFilter httpResponseFilter;
    private final ExecutorService executorService;

    public HttpOutboundHandler() {
        this.httpClient = new HttpClient4();
        this.httpEndpointRouter = new BebopEndpointRouter();
        this.httpResponseFilter = new JetHttpResponseFilter();
        this.executorService = new ThreadPoolExecutor(4, 4, 1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2048));
    }

    public void handle(FullHttpRequest httpRequest, ChannelHandlerContext ctx) {
        executorService.execute(() -> {
            String uri = httpEndpointRouter.route(httpRequest);

            HttpCallBackFunction callback = res -> {
                FullHttpResponse response = null;
                try {
                    byte[] result = EntityUtils.toByteArray(res.getEntity());
                    response = new DefaultFullHttpResponse(
                            HttpVersion.HTTP_1_1,
                            HttpResponseStatus.OK,
                            Unpooled.wrappedBuffer(result));
                    response.headers().set("Content-Type", "application/json");
                    response.headers().setInt("Content-Length", Integer.parseInt(res.getFirstHeader("Content-Length").getValue()));

                    httpResponseFilter.filter(response);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
                    ctx.close();
                } finally {
                    if (response != null) {
                        if (!HttpUtil.isKeepAlive(httpRequest)) {
                            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                        } else {
                            ctx.write(response);
                        }
                    }
                    ctx.flush();
                }
            };

            httpClient.send(uri, callback);
        });
    }

}
