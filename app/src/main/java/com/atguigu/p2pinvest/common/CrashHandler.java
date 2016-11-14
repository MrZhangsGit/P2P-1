package com.atguigu.p2pinvest.common;

import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.atguigu.p2pinvest.utils.UIUtils;

/**
 * Created by 颜银 on 2016/11/12.
 * QQ:443098360
 * 微信：y443098360
 * 作用：程序中的未捕获的全局异常的捕获（单例）
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defultUncaughtExceptionHandler = null;

    private static CrashHandler instance = null;

    private CrashHandler() {
    }

    //懒汉式
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    //初始化
    public void init() {
        if (defultUncaughtExceptionHandler == null) {
            //获取当前系统默未抓捕异常处理器
            defultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        }
        //将此类设置为捕获异常处理类处理器
        Thread.setDefaultUncaughtExceptionHandler(this);//有当前类来处理，不是有系统自带的类处理

    }

    /**
     * 如果出现了未被捕获的异常，则自动调用如下的回调方法
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("TAG", "11111111111111");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //只有主线程才可以调用如下的Looper的方法。将要在主线程中执行的代码放在两个方法之间即可。
                    Looper.prepare();
                    Log.e("TAG", "2222222222222222222");
                    Toast.makeText(UIUtils.getContext(), "亲，出现异常了，你的手机out！！该换了！！", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }).start();


            //收集出现的异常的信息
            collectionException(ex);


            //睡2S
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
            SystemClock.sleep(2000);//方法二，不用抛异常


            //移除所有activity
            ActivityManager.getInstance().removeAll();
            //杀掉当前进程
            android.os.Process.killProcess(android.os.Process.myPid());//杀掉当前的进程
            //关闭徐虚拟机
            System.exit(0);
    }

    /**
     * 收集异常信息的方法，并发送给服务器
     *
     * @param ex
     */
    private void collectionException(Throwable ex) {
        //获取为捕获异常信息
        final String exMessage = ex.getMessage();
        final String message = Build.PRODUCT + "--" + Build.DEVICE + "--" + Build.MODEL + "--" + Build.VERSION.SDK_INT;


        //模拟将获取的异常相关的数据发送给后台。
        new Thread() {
            @Override
            public void run() {
                Log.e("TAG", exMessage);
                Log.e("TAG", message);

            }
        }.start();

    }
}
