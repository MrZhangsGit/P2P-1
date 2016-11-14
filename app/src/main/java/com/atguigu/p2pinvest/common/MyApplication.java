package com.atguigu.p2pinvest.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by 颜银 on 2016/11/12.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class MyApplication extends Application {

    public static Context mContext;
    public static Handler mHandler;
    public static Thread mainThread;
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
        mHandler = new Handler();
        mainThread = Thread.currentThread();//得到当前主线程
        mainThreadId = android.os.Process.myTid();//得到当前主线程id

        //初始化异常未处理类
//        CrashHandler.getInstance().init();
    }

}
