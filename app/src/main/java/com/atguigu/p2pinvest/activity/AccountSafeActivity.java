package com.atguigu.p2pinvest.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class AccountSafeActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.toggle_save)
    ToggleButton toggleSave;
    @Bind(R.id.btn_safe_reset)
    Button btnSafeReset;

    private SharedPreferences sp;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        sp = this.getSharedPreferences("secret_protect", MODE_PRIVATE);
        //读取当前toggleButtond的状态显示
        boolean isOpen = sp.getBoolean("isOpen", false);//读取当前的toggleButton的状态并显示  默认为false
        toggleSave.setChecked(isOpen);

        btnSafeReset.setEnabled(isOpen);//设置button的可操作性

        //toggleSave按钮的改变监听
        toggleSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//开启
                    //判断是否之前设置过手势密码吗？
                    String inputCode = sp.getString("inputCode", "");//判断是否之前设置过手势密码吗？默认返回false

                    if (TextUtils.isEmpty(inputCode)) {//没有
                        new AlertDialog.Builder(AccountSafeActivity.this)
                                .setTitle("是否现在开启手势密码")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(AccountSafeActivity.this, "进入手势密码设置界面", Toast.LENGTH_SHORT).show();
//                                            toggleSave.setChecked(true);
                                        sp.edit().putBoolean("isOpen", true).commit();
                                        //进入手势设置界面
                                        startNewActivity(GestureEditActivity.class, null);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(AccountSafeActivity.this, "取消设置手势密码", Toast.LENGTH_SHORT).show();
                                        toggleSave.setChecked(false);
                                        sp.edit().putBoolean("isOpen", false).commit();
                                    }
                                })
                                .show();
                    } else {//有

                        Toast.makeText(AccountSafeActivity.this, "开启手势密码", Toast.LENGTH_SHORT).show();
//                        toggleSave.setChecked(true);
                        sp.edit().putBoolean("isOpen", true).commit();
                    }
                } else {//关闭状态
                    Toast.makeText(AccountSafeActivity.this, "关闭手势密码", Toast.LENGTH_SHORT).show();
//                    toggleSave.setChecked(false);
                    sp.edit().putBoolean("isOpen", false).commit();
                }

                //结束点击后再重新设置下重新绘制手势密码是否可用
                boolean isOpen = sp.getBoolean("isOpen", false);//读取当前的toggleButton的状态并显示  默认为false
                btnSafeReset.setEnabled(isOpen);//设置button的可操作性

            }
        });

    }

    //启动重新设置新手势密码按钮监听
    @OnClick(R.id.btn_safe_reset)
    public void setBtnSafeReset(View view) {
        startNewActivity(GestureEditActivity.class, null);
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopName.setText("帐户安全");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    //返回键的点击事件
    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        removeCurrentActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_safe;
    }

}
