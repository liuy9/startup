package com.cicada.startup.common.http.retrofit;

import com.cicada.startup.common.config.AppPreferences;
import com.cicada.startup.common.http.BaseURL;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Retrofit
 * <p>
 * 创建时间: 16/5/3 上午10:50 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class RetrofitUtils {

    private static Retrofit instance;


    public static <T> T createService(Class<T> clazz) {

        if (null == instance) {
            synchronized (RetrofitUtils.class) {
                if (null == instance) {

                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder()
//                            .addNetworkInterceptor(new StethoInterceptor())
                            .addInterceptor(new TokenInterceptor())
                            .addInterceptor(logging)
                            .connectTimeout(60 * 5, TimeUnit.SECONDS)
                            .build();
                    instance = new Retrofit.Builder()
                            .baseUrl(BaseURL.getBaseURL())
                            .addConverterFactory(ResponseConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }

        return instance.create(clazz);
    }


    private static class TokenInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder newBuilder = chain.request().newBuilder();
            HttpUrl url = chain.request().url();
            newBuilder.url(url + "?token=" + AppPreferences.getInstance().getLoginToken());
            return chain.proceed(newBuilder.build());

        }
    }
}
