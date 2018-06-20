package com.android.lily.network;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author rape flower
 * @Date 2018-06-20 13:34
 * @Describe Observer
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private static final String TAG = BaseObserver.class.getSimpleName();
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {
        //do nothing
    }

    @Override
    public void onNext(T data) {
        onSuccess(data);
    }

    @Override
    public void onError(Throwable e) {
        android.util.Log.e(TAG, e.toString());
    }

    @Override
    public void onComplete() {
        //do nothing
    }

    /**
     * 请求成功返回结果
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 错误提示
     * @param msg
     */
    protected void onFailure(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
