package com.atguigu.p2pinvest.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by 颜银 on 2016/11/12.
 * QQ:443098360
 * 微信：y443098360
 * 作用：统一应用程序中所有的Activity的栈管理（单例）
 * 涉及到activity的添加、删除指定、删除当前、删除所有、返回栈大小的方法
 */
public class ActivityManager {

    //饿汉式
    private static ActivityManager instance = new ActivityManager();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        return instance;
    }

    private Stack<Activity> activityStack = new Stack<>();

    /**
     * 添加指定的activity到栈中
     *
     * @param activity
     */
    public void add(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    /**
     * 删除指定的activity
     *
     * @param activity
     */
    public void remove(Activity activity) {
        if (activity != null) {
            //错误，size是变动的，这样写会有的activity便利不到，最后的还会可能空指针，或非法参数异常
//            for (int i = 0; i < activityStack.size(); i++) {
//                if (activity.getClass().equals(activityStack.get(i).getClass())) {
//                    activityStack.get(i).finish();
//                    activityStack.remove(i);
//                }
//            }
            //解决办法。用倒叙
            for (int i = activityStack.size() - 1; i >= 0; i--) {
                if (activity.getClass().equals(activityStack.get(i).getClass())) {
                    activityStack.get(i).finish();
                    activityStack.remove(i);
                }
            }
        }
    }

    public Activity getCurrentActivity(){
        return activityStack.lastElement();
    }

    /**
     * 移除当前activity
     */
    public void removeCurrent() {
        //方式一
//        activityStack.get(activityStack.size() - 1).finish();
//        activityStack.remove(activityStack.size() - 1);

        //方式
        activityStack.lastElement().finish();
        activityStack.remove(activityStack.lastElement());
    }

    /**
     * 删除所有activity
     */
    public void removeAll() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            activityStack.get(i).finish();
            activityStack.remove(i);
        }
    }

    /**
     * 返回大小
     * @return
     */
    public int getSize(){
        return activityStack.size();
    }

}
