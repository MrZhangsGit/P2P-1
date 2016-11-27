package com.atguigu.p2pinvest.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 颜银 on 2016/11/19.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}
