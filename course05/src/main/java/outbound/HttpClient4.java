package outbound;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

/**
 * @author oldFlag
 * @since 2022/3/17
 */
public class HttpClient4 {

    private final CloseableHttpAsyncClient httpClient;

    public HttpClient4() {
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(4)
                .setRcvBufSize(32 * 1024)
                .build();
        this.httpClient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response, context) -> 6000)
                .build();
        this.httpClient.start();
    }

    public void send(String uri, HttpCallBackFunction callback) {
        final HttpGet httpget = new HttpGet(uri);

        System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getURI());

        httpClient.execute(httpget, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                callback.execute(httpResponse);
            }

            @Override
            public void failed(Exception e) {
                httpget.abort();
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpget.abort();
            }
        });
        System.out.println("----------------------------------------");
    }
}
