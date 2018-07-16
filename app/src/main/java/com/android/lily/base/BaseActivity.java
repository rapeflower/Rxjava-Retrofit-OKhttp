package com.android.lily.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

/**
 * @Author rape flower
 * @Date 2018-06-19 15:45
 * @Describe
 */
public class BaseActivity extends RxFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
