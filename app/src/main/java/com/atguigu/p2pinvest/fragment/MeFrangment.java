package com.atguigu.p2pinvest.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.BaseFragment;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;

/**
 * Created by 颜银 on 2016/11/11.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class MeFrangment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {

    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.INVISIBLE);
        tvTopName.setText("我的资产");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

}
