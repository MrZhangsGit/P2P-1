package com.atguigu.p2pinvest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseFragment;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 颜银 on 2016/11/11.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class SettingFrangment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.tv_content)
    TextView tvContent;

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
        //为了使得当前的textView获取焦点
        //方式一：
        tvContent.setFocusable(true);
        tvContent.setFocusableInTouchMode(true);
        tvContent.requestFocus();
        //方式二：提供TextView的子视图，同时重写isFocus()
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.INVISIBLE);
        tvTopName.setText("设置");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
