package com.atguigu.p2pinvest.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by 颜银 on 2016/11/14.
 * QQ:443098360
 * 微信：y443098360
 * 作用：动态添加四个提示界面
 * 任何一个联网的view界面展示都有4种情况：
 * ①正在加载 
 * ②加载失败 
 * ③加载成功，但是没有数据
 * ④加载成功，同时返回数据
 */
public abstract class LoadingPager extends FrameLayout {

    //提供4种不同的状态
    private final int STATE_LOADING = 1;//正在加载 
    private final int STATE_ERROR = 2;//加载失败 
    private final int STATE_EMPTY = 3;//加载成功，但是没有数据
    private final int STATE_SUCCESS = 4;//加载成功，同时返回数据

    //当前状态
    private int state_current = STATE_LOADING;

    private Context mContext;

    //提供四种不同的界面
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;


    public LoadingPager(Context context) {
        this(context, null);
    }

    public LoadingPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        //构造器的初始化方法中根据当前的状态，加载不同的界面显示
        init();
    }

    private void init() {

        //指定界面宽高
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if (loadingView == null) {
            loadingView = UIUtils.getView(R.layout.page_loading);
            addView(loadingView, params);//添加到FrameLayout的子类中
        }
        if (errorView == null) {
            errorView = UIUtils.getView(R.layout.page_error);
            addView(errorView, params);
        }
        if (emptyView == null) {
            emptyView = UIUtils.getView(R.layout.page_empty);
            addView(emptyView, params);
        }
//        if(successView == null){
//            successView = UIUtils.getView(R.layout.fragment_setting);
//            addView(successView,params);
//        }

        //根据state_current的值，决定显示哪个具体的View
        showSafePage();
    }

    /**
     * 根据state_current的值，决定显示哪个具体的View
     */
    private void showSafePage() {
        //保证内部的操作在主线程中执行。
        UIUtils.runonUiThread(new Runnable() {
            @Override
            public void run() {
                //加载具体的View
                showPage();
            }
        });
    }

    /**
     * 主线程中：根据state_current的值，决定显示哪个具体的View
     */
    private void showPage() {
        loadingView.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.GONE);
        errorView.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.GONE);

        if (successView == null) {
//            successView = UIUtils.getView(layoutId());
            successView = View.inflate(mContext,layoutId(),null);
            addView(successView);
        }
        successView.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.GONE);
    }

    public abstract int layoutId();

    private ResultState resultState;

    public void show() {

        //如果不需要联网操作界面时
        String url = url();
        if(TextUtils.isEmpty(url)){
            resultState = resultState.SUCCESS;//需要判断为成功
            resultState.setContent("");
            loadPage();//加载四种状态的页面方法
            return;
        }

        //模拟联网操作延迟
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //联网操作
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url(), params(), new AsyncHttpResponseHandler() {

                    //联网成功
                    @Override
                    public void onSuccess(String content) {
                        if (TextUtils.isEmpty(content)) {
                            //联网成功，没有数据
                            resultState = resultState.EMPTY;
                            resultState.setContent("");
                        } else {
                            //联网成功，有数据
                            resultState = resultState.SUCCESS;
                            resultState.setContent(content);
                        }

                        loadPage();
                    }

                    //联网失败
                    @Override
                    public void onFailure(Throwable error, String content) {
                        //联网失败
                        resultState = resultState.ERROR;
                        resultState.setContent("");
                        loadPage();
                    }
                });
            }
        }, 2000);

    }

    /**
     * 加载四种状态的页面方法
     */
    private void loadPage() {
        //设置状态
        switch (resultState) {
            case ERROR:
                state_current = STATE_ERROR;
                break;
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
        }

        //根据state_current的值，决定显示哪个具体的View
        showSafePage();

        if (state_current == STATE_SUCCESS) {//联网成功
            onSuccess(resultState, successView);
        }
    }

    //联网成功，需要实现此方法，并传递进来参数
    protected abstract void onSuccess(ResultState resultState, View successView);

    protected abstract RequestParams params();

    protected abstract String url();

    //提供一个枚举类:将当前联网以后的状态以及可能返回的数据，封装在枚举类中
    public enum ResultState {

        ERROR(2), EMPTY(3), SUCCESS(4);

        int state;
        private String content;//保存的内部数据

        ResultState(int state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
