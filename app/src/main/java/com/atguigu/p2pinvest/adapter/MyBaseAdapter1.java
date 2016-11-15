package com.atguigu.p2pinvest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 颜银 on 2016/11/15.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public abstract class MyBaseAdapter1<T> extends BaseAdapter{

    public List<T> list;

    public MyBaseAdapter1(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return myGetView(position,convertView,parent);
    }

    protected abstract View myGetView(int position, View convertView, ViewGroup parent);

}
