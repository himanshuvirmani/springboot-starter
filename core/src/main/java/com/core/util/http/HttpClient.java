package com.core.util.http;

/**
 * Created by vaibhav.tomar on 06/07/17.
 */


import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public enum HttpClient {
    INSTANCE;

    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient(
            new AsyncHttpClientConfig.Builder()
                    .setConnectTimeout(180000)
                    .setReadTimeout(180000)
                    .setRequestTimeout(180000)
                    .build()
    );

    private HttpClient() {
    }

    public Response executeGet(String url) throws InterruptedException, ExecutionException, IOException {
        HashMap headers = new HashMap();
        HashMap params = new HashMap();
        return this.executeGet(url, params, headers);
    }

    public Response executeGet(String url, Map<String, String> params, Map<String, String> headers) throws InterruptedException, ExecutionException, IOException {
        BoundRequestBuilder requestBuilder = this.asyncHttpClient.prepareGet(url);
        Iterator i$;
        Map.Entry entry;
        if (params != null) {
            i$ = params.entrySet().iterator();

            while (i$.hasNext()) {
                entry = (Map.Entry) i$.next();
                requestBuilder.addQueryParam((String) entry.getKey(), (String) entry.getValue());
            }
        }

        if (headers != null) {
            i$ = headers.entrySet().iterator();

            while (i$.hasNext()) {
                entry = (Map.Entry) i$.next();
                requestBuilder.addHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }

        return (Response) requestBuilder.execute().get();
    }

    public Response executePost(String url, String body, Map<String, String> headers) throws InterruptedException, ExecutionException, IOException {
        FluentCaseInsensitiveStringsMap map = new FluentCaseInsensitiveStringsMap();
        Iterator i$ = headers.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            map.add((String) entry.getKey(), new String[]{(String) entry.getValue()});
        }

        return (Response) this.asyncHttpClient.preparePost(url).setBody(body).setHeaders(map).execute().get();
    }

    public Response executePut(String url, String body, Map<String, String> headers) throws InterruptedException, ExecutionException, IOException {
        FluentCaseInsensitiveStringsMap map = new FluentCaseInsensitiveStringsMap();
        Iterator i$ = headers.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry entry = (Map.Entry) i$.next();
            map.add((String) entry.getKey(), new String[]{(String) entry.getValue()});
        }

        return (Response) this.asyncHttpClient.preparePut(url).setBody(body).setHeaders(map).execute().get();
    }
}
