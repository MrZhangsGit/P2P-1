package com.atguigu.p2pinvest.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.atguigu.p2pinvest.bean.User;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

/**
 * Created by 颜银 on 2016/11/16.
 * QQ:443098360
 * 微信：y443098360
 * 作用：Activity的基类
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        //添加到activity管理里面
        ActivityManager.getInstance().add(this);

        initTitle();
        //初始化监听
        initData();

        initListener();

    }

    //初始化监听
    protected abstract void initListener();

    //初始化数据
    protected abstract void initData();

    //初始化标题
    protected abstract void initTitle();

    //初始化布局id
    protected abstract int getLayoutId();

    //销毁当前的activity
    public void removeCurrentActivity() {
        ActivityManager.getInstance().removeCurrent();
    }

    //启动一个Activity
    public void startNewActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);

        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);

    }

    //销毁所有activity
    public void removeAllActivity() {
        ActivityManager.getInstance().removeAll();
    }

    //使用AsyncHttpClient，实现联网的声明
    public AsyncHttpClient client = new AsyncHttpClient();


    //保存用户信息的操作:使用sp存储
    public void saveUser(User user) {
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("UF_ACC", user.UF_ACC);
        editor.putString("UF_AVATAR_URL", user.UF_AVATAR_URL);
        editor.putString("UF_IS_CERT", user.UF_IS_CERT);
        editor.putString("UF_PHONE", user.UF_PHONE);

        editor.commit();//只有提交以后，才可以创建此文件，并保存数据---千万别忘记
    }

    //读取数据，得到内存中的User对象
    public User readUser() {
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);

        User user = new User();
        user.UF_ACC = sp.getString("UF_ACC", "");
        user.UF_AVATAR_URL = sp.getString("UF_AVATAR_URL", "");
        user.UF_IS_CERT = sp.getString("UF_IS_CERT", "");
        user.UF_PHONE = sp.getString("UF_PHONE", "");

        //返回数据
        return user;
    }
}
