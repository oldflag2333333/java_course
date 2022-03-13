package com.example.course04;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author oldFlag
 * @since 2022/3/13
 */
public class HttpClient01 {

    private final static String URI = "http://localhost:8803";


    public static void main(final String[] args) throws Exception {
        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpget = new HttpGet(URI);

            System.out.println("Executing request " + httpget.getMethod() + " " + httpget.getURI());

            // Create a custom response handler
            final ResponseHandler<String> responseHandler = response -> {
                final int status = response.getStatusLine().getStatusCode();
                if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_TEMPORARY_REDIRECT) {
                    final HttpEntity entity = response.getEntity();
                    try {
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } catch (final ParseException ex) {
                        throw new ClientProtocolException(ex);
                    }
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            final String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        }
    }


}
