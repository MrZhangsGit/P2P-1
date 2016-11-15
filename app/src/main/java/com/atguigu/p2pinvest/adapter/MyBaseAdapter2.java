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
public abstract class MyBaseAdapter2<T> extends BaseAdapter{

    public List<T> list;

    public MyBaseAdapter2(List<T> list) {
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
//        ViewHolder holder;
        if(convertView == null){
            convertView = View.inflate(parent.getContext(),getItemLayout(),null);
//            holder = new ViewHolder(convertView);//静态的ViewHolder也可以不写此处
        }else {
//            holder = (ViewHolder) convertView.getTag();
        }
        //装配数据
        T t = list.get(position);
        setData(convertView,t);

        return convertView;
    }

    //设置数据
    protected abstract void setData(View convertView, T t);

    //加载布局
    protected abstract int getItemLayout();

//    class ViewHolder{
//        public ViewHolder(View convertView) {
//            convertView.setTag(convertView);
//        }
//    }

}
