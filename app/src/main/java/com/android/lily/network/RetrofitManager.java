package com.android.lily.network;

import com.android.lily.utils.Inspect;

import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author rape flower
 * @Date 2018-04-19 17:41
 * @Describe Retrofit管理类
 */
public class RetrofitManager {

    private static final int TIME_OUT = 10;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    /**
     * 初始化
     * 1.为OkHttpClient配置参数
     * 2.初始设置Retrofit
     *
     * @param baseUrl set the API base URL.
     */
    public static void init(String baseUrl) {
        /****** OkHttpClient 配置基本参数 ******/
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        // 设置连接超时时间
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置写操作超时时间
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //设置读操作超时时间
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置重定向
        okHttpClientBuilder.followRedirects(true);
        /****** 添加https支持 ******/
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        // 信任所有Https，使用OkHttpClient默认的SSLSocketFactory
        // X509TrustManager trustManager = systemDefaultTrustManager();
        // this.sslSocketFactory = systemDefaultSslSocketFactory(trustManager);
        okHttpClient = okHttpClientBuilder.build();

        /****** Retrofit 基本配置 ******/
        retrofit = buildRetrofit(okHttpClient, baseUrl);
    }

    /**
     * 根据条件构建对应的Retrofit
     *
     * @return
     */
    private static Retrofit buildRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                // 添加数据解析ConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                // 添加RxJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * Default global Retrofit instance
     *
     * @return
     */
    public static Retrofit getRetrofit() {
        Inspect.asserts((retrofit != null), "Please call the RetrofitManager.init() when your application is started.");
        return retrofit;
    }

    /**
     * Get a new Retrofit instance by baseUrl.
     *
     * @return
     */
    public static Retrofit createRetrofit(String baseUrl) {
        Inspect.asserts((retrofit != null), "Please call the RetrofitManager.init() when your application is started.");
        return buildRetrofit(okHttpClient, baseUrl);
    }
}
