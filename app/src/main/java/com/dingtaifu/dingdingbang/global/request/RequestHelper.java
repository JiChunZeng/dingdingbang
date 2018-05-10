package com.dingtaifu.dingdingbang.global.request;

import com.dingtaifu.dingdingbang.global.AppConstant;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sakamichi on 2017/7/5.
 */

public class RequestHelper {

    private Retrofit mRetrofit;
    private volatile static RequestHelper instance;

    public static RequestHelper getInstance() {
        if (instance == null) {
            synchronized (RequestHelper.class) {
                if (instance == null) {
                    instance = new RequestHelper();
                }
            }
        }
        return instance;
    }

    private RequestHelper() {
        OkHttpClient.Builder client = new OkHttpClient
                .Builder()
                .connectTimeout(25000, TimeUnit.MILLISECONDS)
                .readTimeout(25000, TimeUnit.MILLISECONDS)
                .writeTimeout(25000, TimeUnit.MILLISECONDS);

        GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
        mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_URL)
                .client(client.build())
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


}
