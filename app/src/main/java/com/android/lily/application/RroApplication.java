package com.android.lily.application;

import android.app.Application;

import com.android.lily.network.RetrofitManager;

/**
 * @Author rape flower
 * @Date 2018-04-23 16:10
 * @Describe
 */
public class RroApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.init("http://www.kuaidi100.com/");
    }
}
