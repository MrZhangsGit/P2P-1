package com.atguigu.p2pinvest.adapter;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by 颜银 on 2016/11/15.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public abstract class BaseHolder<T> {
    private T data;
    private View rootView;

    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);
        ButterKnife.bind(this, rootView);
    }
    public View getRootView() {
        return rootView;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refreshData();
    }
    //装配数据
    protected abstract void refreshData();
    //提供item layout
    public abstract View initView();
}
