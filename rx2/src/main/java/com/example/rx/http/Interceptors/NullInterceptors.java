package com.example.rx.http.Interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Vinctor on 2016/6/23.
 */
public class NullInterceptors implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        Response response = chain.proceed(request);
        final ResponseBody responseBody = response.body();
        String type = responseBody.contentType().toString();
        String content = responseBody.string();
        if (type.contains("text") || type.contains("json") || type.contains("xml")) {
            if (content.contains("null")) {
                try {
                    content = content.replaceAll("null", "\"\"");
                } catch (Exception e) {

                }
            }
        }
        return response.newBuilder().body(ResponseBody.create(responseBody.contentType(), content)).build();
    }
}
