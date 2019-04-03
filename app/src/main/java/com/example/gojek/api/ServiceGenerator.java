package com.example.gojek.api;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static String baseUrl = "https://api.apixu.com/v1/";
    private static final int CONNECT_TIME_OUT_SEC = 60;
    private static final int READ_TIME_OUT_SEC = 30;
    private static final int WRITE_TIME_OUT_SEC = 30;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()));

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor
            .Level.BODY);

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME_OUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        builder.client(httpClient);
        retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    public static OkHttpClient getOkHttpClient() {
        return httpClient;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String url) {
        builder.baseUrl(url);
    }
}
