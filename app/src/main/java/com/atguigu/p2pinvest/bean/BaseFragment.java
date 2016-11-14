package com.atguigu.p2pinvest.bean;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.p2pinvest.ui.LoadingPager;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by 颜银 on 2016/11/11.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPager loadingPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        //绑定布局资源
//        View view = UIUtils.getView(getLayoutId());
//        ButterKnife.bind(this, view);
//
//        //初始化标题
//        initTitle();
//
//        //初始化数据
//        initData();

        loadingPager = new LoadingPager(getActivity()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected void onSuccess(ResultState resultState, View successView) {//
                ButterKnife.bind(BaseFragment.this, successView);//绑定布局
                //初始化标题
                initTitle();

                //初始化数据
                initData(resultState.getContent());
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };

        return loadingPager;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //实现联网操作的方法
        show();
    }

    /**
     * 实现联网操作的方法
     */
    private void show() {
        //还有不需要联网的界面，写在这里不合适
//        UIUtils.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                loadingPager.show();
//            }
//        },2000);

        loadingPager.show();
    }

    /**
     * 加载布局是否充满
     */
    protected abstract RequestParams getParams();

    /**
     * 获得联网路径
     * @return
     */
    protected abstract String getUrl();

    /**
     * 初始化数据的方法
     * @param content
     *
     */
    protected abstract void initData(String content);

    /**
     * 初始化标题的方法
     *
     */
    protected abstract void initTitle();

    /**
     * 得到添加布局资源id的方法
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife解绑定
        ButterKnife.unbind(this);
    }
}
