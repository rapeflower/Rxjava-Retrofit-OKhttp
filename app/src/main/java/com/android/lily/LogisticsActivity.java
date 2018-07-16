package com.android.lily;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.android.lily.adapter.LogisticsAdapter;
import com.android.lily.base.BaseActivity;
import com.android.lily.model.Logistics;
import com.android.lily.network.BaseObserver;
import com.android.lily.network.RetrofitManager;
import com.android.lily.network.RxSchedulers;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author lily
 * @date 2018-07-18 10:12
 * @describe 物流信息界面
 */
public class LogisticsActivity extends BaseActivity {

    private SmartRefreshLayout refreshLayout;
    private ListView lvLogistics;
    private LogisticsAdapter logisticsAdapter;
    private List<Logistics.Details> detailsList;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);

        refreshLayout = findViewById(R.id.refreshLayout);
        lvLogistics = findViewById(R.id.lv_logistics);

        initVariable();
        loadLogistics();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        detailsList = new ArrayList<>();
        logisticsAdapter = new LogisticsAdapter(this, detailsList);

        lvLogistics.setAdapter(logisticsAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                detailsList.clear();
                loadLogistics();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(3000);
            }
        });
    }

    /**
     * 查询快递信息
     */
    private void loadLogistics() {
        Observable<Logistics> observable = RetrofitManager.getApiService().hqLogisticsBy("zhongtong", "474944203605");
        observable.compose(RxSchedulers.compose(this.<Logistics>bindToLifecycle())).subscribe(new BaseObserver<Logistics>(LogisticsActivity.this) {

            @Override
            protected void onSuccess(Logistics logistics) {
                Log.w("@@@", "loadLogistics = " + logistics.getCondition());
                detailsList.addAll(logistics.getData());
                logisticsAdapter.notifyDataSetChanged();
                if (isRefresh) {
                    refreshLayout.finishRefresh(2000);
                }
            }

            @Override
            protected void onFailure(String msg) {

            }
        });
    }
}
