package com.atguigu.p2pinvest;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.atguigu.p2pinvest.common.BaseActivity;
import com.atguigu.p2pinvest.fragment.HomeFrangment;
import com.atguigu.p2pinvest.fragment.InvestFrangment;
import com.atguigu.p2pinvest.fragment.MeFrangment;
import com.atguigu.p2pinvest.fragment.SettingFrangment;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.fl_main)
    FrameLayout flMain;

    @Bind(R.id.rg_mian)
    RadioGroup rgMian;

    private HomeFrangment homeFrangment;
    private InvestFrangment investFrangment;
    private MeFrangment meFrangment;
    private SettingFrangment settingFrangment;

    private FragmentTransaction transaction;


    protected void initListener() {
        rgMian.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_main_home://主页
                        setSelect(0);
                        break;
                    case R.id.rb_main_invent://投资
                        setSelect(1);
                        break;
                    case R.id.rb_main_me://我的资产
                        setSelect(2);
                        break;
                    case R.id.rb_main_setting://设置
                        setSelect(3);
                        break;
                }

            }
        });

        //默认选择首页
        rgMian.check(R.id.rb_main_home);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void setSelect(int select) {
        //开启事务----开启一次提交一次
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        //先隐藏所有fragment显示
        hintFragment();

        if (select == 0) {
            if (homeFrangment == null) {
                homeFrangment = new HomeFrangment();
                transaction.add(R.id.fl_main, homeFrangment);//创建对象以后，并不会马上执行其生命周期方法，而是在事务执行commit()，才会执行生命周期方法
            }
            transaction.show(homeFrangment);
        } else if (select == 1) {
            if (investFrangment == null) {
                investFrangment = new InvestFrangment();
                transaction.add(R.id.fl_main, investFrangment);//创建对象以后，并不会马上执行其生命周期方法，而是在事务执行commit()，才会执行生命周期方法
            }
            transaction.show(investFrangment);
        } else if (select == 2) {
            if (meFrangment == null) {
                meFrangment = new MeFrangment();
                transaction.add(R.id.fl_main, meFrangment);//创建对象以后，并不会马上执行其生命周期方法，而是在事务执行commit()，才会执行生命周期方法
            }
            transaction.show(meFrangment);
        } else if (select == 3) {
            if (settingFrangment == null) {
                settingFrangment = new SettingFrangment();
                transaction.add(R.id.fl_main, settingFrangment);//创建对象以后，并不会马上执行其生命周期方法，而是在事务执行commit()，才会执行生命周期方法
            }
            transaction.show(settingFrangment);
        }

        //提交--千万别忘记
        transaction.commit();
    }

    //先隐藏所有fragment显示
    private void hintFragment() {
        //如果显示之前不隐藏别的，title会显示最后setting的title
        if (homeFrangment != null) {
            transaction.hide(homeFrangment);
        }
        if (investFrangment != null) {
            transaction.hide(investFrangment);
        }
        if (meFrangment != null) {
            transaction.hide(meFrangment);
        }
        if (settingFrangment != null) {
            transaction.hide(settingFrangment);
        }
    }

    /**
     * 实现2s内点击两次退出键才退出
     *
     * @param keyCode
     * @param event
     * @return
     */

    private Handler handler = new Handler();
    private boolean isOut = true;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isOut) {
            Toast.makeText(MainActivity.this, "在点一次就退出", Toast.LENGTH_SHORT).show();
            isOut = false;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isOut = true;//2s后初始化isOut为原来状态
                }
            }, 2000);

            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
