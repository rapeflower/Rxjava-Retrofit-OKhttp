package com.android.lily.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.lily.R;
import com.android.lily.base.AbstractBaseAdapter;
import com.android.lily.base.AbstractViewHolder;
import com.android.lily.model.Logistics;

import java.util.List;

/**
 * @author lily
 * @date 2018-07-16 10:16
 * @describe 物流信息适配器
 */
public class LogisticsAdapter extends AbstractBaseAdapter<Logistics.Details> {

    public LogisticsAdapter(Context context, List<Logistics.Details> data) {
        super(context, data);
    }

    @Override
    public int onBindLayout() {
        return R.layout.logistics_item;
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, Logistics.Details data, int position) {
        LogisticsHolder lh = (LogisticsHolder) holder;
        lh.tvTime.setText(data.getTime());
        lh.tvContext.setText(data.getContext());
        lh.tvFtime.setText(data.getFtime());
    }

    @Override
    public AbstractViewHolder onCreateViewHolder() {
        return new LogisticsHolder();
    }

    private class LogisticsHolder extends AbstractViewHolder {
        TextView tvTime;
        TextView tvContext;
        TextView tvFtime;

        @Override
        public void initWidget(View view) {
            tvTime = view.findViewById(R.id.tv_time);
            tvContext = view.findViewById(R.id.tv_context);
            tvFtime = view.findViewById(R.id.tv_ftime);
        }
    }
}
