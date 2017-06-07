package com.example.rx.http.Retrofit;


import com.blankj.utilcode.utils.NetworkUtils;
import com.example.mylibrary.interfaces.Constants;
import com.example.rx.appconfig.MyApplication;
import com.example.rx.http.Converts.Scalars.ScalarsConverterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static Retrofit singleton;
    private static Retrofit cacheLeton;

    public static <T> T createApi(Class<T> clazz) {
        if (singleton == null) {
            synchronized (RetrofitFactory.class) {
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                if (Constants.isDebug) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    httpClient.addInterceptor(logging);
                }
                Builder builder = new Builder();
                httpClient.connectTimeout(Constants.networkTimeout, TimeUnit.SECONDS);
                singleton = builder.baseUrl("http://www.52doubao.cn/")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build()).build();
            }
        }
        return singleton.create(clazz);
    }

    /**
     * 带缓存的
     */
    public static <T> T createCacheApi(Class<T> clazz) {
        if (cacheLeton == null) {
            synchronized (RetrofitFactory.class) {
                File cacheFile = new File(MyApplication.appContext.getCacheDir(), "ZhiBookCache");
//                Log.i("okhttp",cacheFile.getPath());
                Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                if (Constants.isDebug) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    httpClient.addInterceptor(logging);
                }
                httpClient.addNetworkInterceptor(mCacheInterceptor)
                        .addInterceptor(mCacheInterceptor)
                        .cache(cache)
                        .connectTimeout(Constants.networkTimeout, TimeUnit.SECONDS)
                        .readTimeout(Constants.networkTimeout, TimeUnit.SECONDS);
                Builder builder = new Builder();

                cacheLeton = builder.baseUrl(Constants.baseUrl)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build()).build();
            }
        }
        return cacheLeton.create(clazz);
    }

    // 云端响应头拦截器，用来配置缓存策略
    // 设缓存有效期为两天
    private static final int CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    // 2秒内直接读缓存
    private static final int CACHE_AGE_SEC = 2;
    private static Interceptor mCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            // 在这里统一配置请求头缓存策略以及响应头缓存策略
            if (NetworkUtils.isAvailableByPing()) {
                // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
            } else {
                // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            }

        }
    };
}
