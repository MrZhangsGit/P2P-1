package com.atguigu.p2pinvest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.p2pinvest.MainActivity;
import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.ActivityManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends Activity {

    //版本号
    @Bind(R.id.tv_welcome_version)
    TextView tvWelcomeVersion;

    @Bind(R.id.rl_welcome)
    RelativeLayout rlWelcome;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager.getInstance().add(this);

        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        setAnimation();
    }

    private void setAnimation() {

        //透明度动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);//时长
//        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());//变化率

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);//保持最终样子

        //发消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                //销毁当前页面
                finish();
            }
        }, 3000);

//       //设置动画监听
//        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                //销毁当前页面
//                finish();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        //合成动画
        AnimationSet animationSet = new AnimationSet(true);

        //添加动画
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        //启动动画
        rlWelcome.startAnimation(animationSet);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
