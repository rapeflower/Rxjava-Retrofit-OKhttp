package com.android.lily;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.lily.business.ApiService;
import com.android.lily.model.Logistics;
import com.android.lily.network.BaseObserver;
import com.android.lily.network.RetrofitManager;
import com.android.lily.network.RxSchedulers;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author rape flower
 * @Date 2018-04-19 14:49
 * @Describe
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Retrofit网络请求
     * @param view
     */
    public void request(View view) {
        getKd_4();
    }

    /**
     * 获取Github用户信息
     */
    private void getUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> repos = apiService.listRepos("rapeflower");
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    android.util.Log.w(TAG, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 查询快递信息
     */
    private void getKd_1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> repos = apiService.query("zhongtong", "474944203605");
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.w(TAG, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 查询快递信息
     */
    private void getKd_2() {
        Log.w(TAG, "Retrofit = " + RetrofitManager.getRetrofit());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Logistics> repos = apiService.getLogistics("zhongtong", "474944203605");
        repos.enqueue(new Callback<Logistics>() {
            @Override
            public void onResponse(Call<Logistics> call, Response<Logistics> response) {
                Log.w(TAG, response.body().getNu());
            }

            @Override
            public void onFailure(Call<Logistics> call, Throwable t) {

            }
        });
    }

    /**
     * 查询快递信息
     */
    private void getKd_3() {
        ApiService apiService = RetrofitManager.getRetrofit().create(ApiService.class);
        Observable<Logistics> observable = apiService.getLogisticsByRx("zhongtong", "474944203605");
        // 请求网络切换异步线程（IO线程）
        observable.subscribeOn(Schedulers.io())
                // 响应结果处理切换到主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Logistics>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Logistics logistics) {
                        Log.w(TAG, "result = " + logistics.getNu());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 查询快递信息
     */
    private void getKd_4() {
        Observable<Logistics> observable = RetrofitManager.getApiService().hqLogisticsBy("zhongtong", "474944203605");
        observable.compose(RxSchedulers.compose(this.<Logistics>bindToLifecycle())).subscribe(new BaseObserver<Logistics>(MainActivity.this) {

            @Override
            protected void onSuccess(Logistics logistics) {
                Log.w(TAG, "result = " + logistics.toString());
            }
        });
    }
}
