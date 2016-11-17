package com.atguigu.p2pinvest.fragment;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.BaseFragment;
import com.atguigu.p2pinvest.ui.randomLayout.StellarMap;
import com.atguigu.p2pinvest.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.Bind;


public class ProductRecommondFragment extends BaseFragment {

    @Bind(R.id.stellar_map)
    StellarMap stellarMap;

    private Random random;

    //数据集合
    private String[] datas = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划",
            "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"
    };

    //将以上数据分为两组
    private String[] ones = new String[datas.length / 2];
    private String[] twos = new String[datas.length - ones.length];


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
        //初始化分成的两个子数组
        for (int i = 0; i < datas.length / 2; i++) {
            ones[i] = datas[i];
        }

        for (int i = 0; i < datas.length - datas.length / 2; i++) {
            twos[i] = datas[datas.length / 2 + i];
        }

        //设置随机
        random = new Random();
        MyAdapter adapter = new MyAdapter();
        stellarMap.setAdapter(adapter);

        //随机设置内边距
        int leftPadding = random.nextInt(10);
        int rightPadding = random.nextInt(10);
        int topPadding = random.nextInt(10);
        int bottomPadding = random.nextInt(10);
        stellarMap.setInnerPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        //如下两个方法如下不调用，不会在界面显示随机数据的效果
        stellarMap.setRegularity(8, 8);
        stellarMap.setGroup(0, true);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_recommond;
    }


    class MyAdapter implements StellarMap.Adapter {

        /**
         * 获取组别个数
         * 即分成几组数据，这里分成两组ones 和 twos
         *
         * @return
         */
        @Override
        public int getGroupCount() {
            return 2;
        }

        /**
         * 返回每组各有多少条数据
         *
         * @param group
         * @return
         */
        @Override
        public int getCount(int group) {
            if (group == 0) {
                return datas.length / 2;
            } else {
                return datas.length - datas.length / 2;
            }
        }

        /**
         * 装配数据
         *
         * @param group
         * @param position
         * @param convertView
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView textView = new TextView(getContext());
            //设置属性
            if (group == 0) {
                textView.setText(ones[position]);//设置显示内容
            } else {
                textView.setText(twos[position]);//设置显示内容
            }

            //设置字体大小，随机字体大小
            textView.setTextSize(UIUtils.dp2px(8 + random.nextInt(10)));
            //设置字体随机颜色
            int red = random.nextInt(211);//red:[0,255]  00~ff
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            textView.setTextColor(Color.rgb(red, green, blue));//设置随机颜色

            //设置每一个textView的点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductRecommondFragment.this.getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        //下一次要显示的组别
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (group == 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
