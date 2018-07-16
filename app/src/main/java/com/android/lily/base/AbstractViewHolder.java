package com.android.lily.base;

import android.view.View;

/***********
 * @Author rape flower
 * @Date 2017-12-04 16:48
 * @Describe 通用View缓存类（容器），也就是我们常说的'ViewHolder'，
 * 在数据适配器中创建的ViewHolder都需要集成此类。
 */
public abstract class AbstractViewHolder {

    /**
     * 初始化View
     * <p>在此方法中通过view.findViewById(R.id.xxx)来实例化View</p>
     * @param view
     */
    public abstract void initWidget(View view);
}
