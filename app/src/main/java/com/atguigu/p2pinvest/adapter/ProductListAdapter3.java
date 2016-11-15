package com.atguigu.p2pinvest.adapter;

import com.atguigu.p2pinvest.bean.ProInfo;

import java.util.List;

/**
 * Created by 颜银 on 2016/11/15.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class ProductListAdapter3 extends MyBaseAdapter3<ProInfo> {
    public ProductListAdapter3(List<ProInfo> list) {
        super(list);
    }

    @Override
    protected BaseHolder getHolder() {
        return new MyHolder();
    }
}
