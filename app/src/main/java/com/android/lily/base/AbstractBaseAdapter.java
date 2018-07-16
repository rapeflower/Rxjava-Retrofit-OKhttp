package com.android.lily.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/***********
 * @Author rape flower
 * @Date 2017-12-04 16:48
 * @Describe 通用数据适配器类
 */
public abstract class AbstractBaseAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mData;
	protected LayoutInflater mInflater;

	public AbstractBaseAdapter(Context context, List<T> data) {
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		this.mData = data;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public T getItem(int position) {
		return mData == null ? (T) null : mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AbstractViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(onBindLayout(), parent, false);
			holder = onCreateViewHolder();
			if (holder == null) {
				throw new NullPointerException("The view holder object is null, you must instance a view holder.");
			}
			holder.initWidget(convertView);
			convertView.setTag(holder);
		} else {
			holder = (AbstractViewHolder) convertView.getTag();
		}
		onBindViewHolder(holder, getItem(position), position);
		return convertView;
	}

	/**
	 * 设置View显示
	 *
	 * @param view
	 */
	public void setVisible(View view) {
		if (view == null) return;
		if (view.getVisibility() == View.GONE) {
			view.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 设置View隐藏且不占空间
	 *
	 * @param view
	 */
	public void setGone(View view) {
		if (view == null) return;
		if (view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置View隐藏但占空间
	 *
	 * @param view
	 */
	public void setInvisible(View view) {
		if (view == null) return;
		if (view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 绑定布局文件
	 * 传入的是布局文件的ID
	 *
	 * @return 布局文件的资源ID（ex: R.layout.xxx）
	 */
	public abstract int onBindLayout();

	/**
	 * 将数据一一对应绑定到视图（View）
	 *
	 * @param holder 缓存View的容器
	 * @param data 数据
	 * @param position 索引
	 */
	public abstract void onBindViewHolder(AbstractViewHolder holder, T data, int position);

	/**
	 * 创建View缓存容器
	 *
	 * @return 缓存View的容器
	 */
	public abstract AbstractViewHolder onCreateViewHolder();
}
