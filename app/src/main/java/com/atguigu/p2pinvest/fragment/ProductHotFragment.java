package com.atguigu.p2pinvest.fragment;


import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseFragment;
import com.atguigu.p2pinvest.ui.FlowLayout;
import com.atguigu.p2pinvest.utils.DrawUtils;
import com.atguigu.p2pinvest.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.Bind;


public class ProductHotFragment extends BaseFragment {

    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    private String[] datas = new String[]{"新手计划", "乐享活系列90天计划", "钱包", "30天理财计划(加息2%)",
            "林业局投资商业经营与大捞一笔", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍", "Java培训老师自己周转",
            "HelloWorld", "C++-C-ObjectC-java", "Android vs ios", "算法与数据结构", "JNI与NDK", "team working"};

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
        Random random = new Random();

        for (String data : datas) {
            final TextView textView = new TextView(getActivity());

            //设置textView的边距对象
            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mp.leftMargin = UIUtils.dp2px(10);
            mp.topMargin = UIUtils.dp2px(10);
            mp.rightMargin = UIUtils.dp2px(10);
            mp.bottomMargin = UIUtils.dp2px(10);
            //设置边距
            textView.setLayoutParams(mp);

            textView.setText(data);
            textView.setTextSize(UIUtils.dp2px(8));

            //设置背景颜色
            int red = random.nextInt(211);
            int green = random.nextInt(211);
            int blue = random.nextInt(211);

            //方式一:设置TextView的背景颜色
//            textView.setBackground(DrawUtils.getDrawable(Color.rgb(red,green,blue),UIUtils.dp2px(5)));
            //方式二:设置TextView的背景颜色 + 点击选中效果的变化颜色
            textView.setBackground(DrawUtils.getSelector(DrawUtils.getDrawable(Color.rgb(red, green, blue), UIUtils.dp2px(5)), DrawUtils.getDrawable(Color.WHITE, UIUtils.dp2px(5))));

            //设置TextView可点击方法
//            textView.setClickable(true);

            //当设置了点击事件时，默认textView就是可点击的了。
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductHotFragment.this.getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            //设置内边距：
            int padding = UIUtils.dp2px(5);
            textView.setPadding(padding, padding, padding, padding);

            //添加视图
            flowLayout.addView(textView);

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_hot;
    }

}
