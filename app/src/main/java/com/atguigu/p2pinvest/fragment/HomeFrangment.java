package com.atguigu.p2pinvest.fragment;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseFragment;
import com.atguigu.p2pinvest.bean.Imags;
import com.atguigu.p2pinvest.bean.Index;
import com.atguigu.p2pinvest.bean.ProInfo;
import com.atguigu.p2pinvest.common.AppNetConfig;
import com.atguigu.p2pinvest.ui.RoundProgress;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 颜银 on 2016/11/11.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class HomeFrangment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.roundProgress)
    RoundProgress roundProgress;
    @Bind(R.id.tv_home_interest)
    TextView tvHomeInterest;
    @Bind(R.id._btn_home_join)
    Button BtnHomeJoin;

    private Index index;

    @Override
    protected RequestParams getParams() {
//        return null;
        return new RequestParams();//可以创建一个空的  和null效果差不多
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected void initData(String content) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(AppNetConfig.INDEX, new AsyncHttpResponseHandler() {
            //联网下载成功
            @Override
            public void onSuccess(String content) {
                Log.e("TAG", "联网成功");
                //1.使用fastJson解析得到的json数据,并封装数据到java对象中
                JSONObject jsonObject = JSON.parseObject(content);

                //获得下面圆环的json信息
                String proInfo = jsonObject.getString("proInfo");
                ProInfo mProInfo = JSON.parseObject(proInfo, ProInfo.class);

                //获得轮播图的信息
                String imags = jsonObject.getString("imageArr");
                List<Imags> mImags = JSON.parseArray(imags, Imags.class);

                index = new Index();
                index.proInfo = mProInfo;
                index.imags = mImags;


                //2.设置Banner显示图片
                //设置banner样式
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片url集合:imageUrl
                List<String> imageUrl = new ArrayList<String>(mImags.size());
                for (int i = 0; i < mImags.size(); i++) {
                    imageUrl.add(mImags.get(i).getIMAURL());
                    Log.e("TAG", "url = " + mImags.get(i).getIMAURL());
                }

                banner.setImages(imageUrl);
                //设置banner动画效果   Transformer.CubeOut //立体效果
                banner.setBannerAnimation(Transformer.FlipVertical);
                //设置标题集合（当banner样式有显示title时）
                String[] titles = new String[]{"深情不及久伴,加息2%","乐享活计划","破茧重生", "安心钱包计划，年化6%"};
                banner.setBannerTitles(Arrays.asList(titles));
                //设置自动轮播，默认为true
                banner.isAutoPlay(true);
                //设置轮播时间
                banner.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.RIGHT);
                //banner设置方法全部调用完毕时最后调用
                banner.start();


                //3.根据得到的产品的数据，更新界面中的产品展示
                String yearRate = index.proInfo.getYearRate();
                tvHomeInterest.setText(yearRate + "%");


                //4.显示圈圈进度  模拟圆环动起来
                final int totalProgress = Integer.parseInt(index.proInfo.getProgress());
//                roundProgress.setProgress(Integer.parseInt(progress));
                Log.e("TAG", "totalProgress = " + totalProgress);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        roundProgress.setMax(100);
                        roundProgress.setProgress(0);
                        for (int i = 0; i < totalProgress; i++) {
                            roundProgress.setProgress(roundProgress.getProgress() + 1);

                            //睡眠-模拟进度
                            SystemClock.sleep(20);

//                            roundProgress.invalidate();//强制重绘（只能主线程可以用）
                            roundProgress.postInvalidate();//强制重绘（主/分线程都可以）
                        }
                    }
                }).start();

            }

            //联网下载失败
            @Override
            public void onFailure(Throwable error, String content) {
                Log.e("TAG", "联网失败");
            }

        });
    }

    @Override
    protected void initTitle() {
        //隐藏左右的ImageView 的按钮
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.INVISIBLE);
        tvTopName.setText("首页");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    public class GlideImageLoader implements ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }
    }
}
