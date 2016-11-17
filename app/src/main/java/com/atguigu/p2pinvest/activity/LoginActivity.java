package com.atguigu.p2pinvest.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.p2pinvest.MainActivity;
import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.User;
import com.atguigu.p2pinvest.common.AppNetConfig;
import com.atguigu.p2pinvest.common.BaseActivity;
import com.atguigu.p2pinvest.utils.MD5Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.log_ed_mob)
    EditText logEdMob;
    @Bind(R.id.about_com)
    RelativeLayout aboutCom;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.log_ed_pad)
    EditText logEdPad;
    @Bind(R.id.log_log_btn)
    Button logLogBtn;

    @Override
    protected void initData() {
        //
    }

    @Override
    protected void initListener() {
        logLogBtn.setOnClickListener(new MyOnClickListener());
        //返回
        ivTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //销毁所有activity
                LoginActivity.this.removeAllActivity();
                //重新加载主Activity
                LoginActivity.this.startNewActivity(MainActivity.class,null);
            }
        });
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopName.setText("用户登录");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //1.获取用户信息
            String userName = logEdMob.getText().toString().trim();
            String password = logEdPad.getText().toString().trim();
            //判空检验
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            //联网校验
            final String url = AppNetConfig.LOGIN;

            //2.联网将用户数据发送给服务器，其中手机号和密码作为请求参数
            RequestParams params = new RequestParams();
            params.put("username", userName);
            params.put("password", MD5Utils.MD5(password));//加密
            client.post(url, params, new AsyncHttpResponseHandler() {

                //3.得到响应数据：成功
                @Override
                public void onSuccess(String content) {
                    //解析
                    JSONObject jsonObject = JSON.parseObject(content);
                    Boolean isSuccess = jsonObject.getBoolean("success");
                    if (isSuccess) {//联网成功--密码正确

                        String data = jsonObject.getString("data");

                        //解析
                        User user = JSON.parseObject(data, User.class);

                        //保存得到的用户信息（使用sp存储）
                        saveUser(user);

                        //重新加载页面，显示用户的信息在MeFragment中
                        LoginActivity.this.removeAllActivity();
                        LoginActivity.this.startNewActivity(MainActivity.class, null);//重新加载主activity
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                    } else {//联网成功--密码错误
                        Toast.makeText(LoginActivity.this, "用户名不存在或密码不正确", Toast.LENGTH_SHORT).show();
                    }
                }

                //联网失败
                @Override
                public void onFailure(Throwable error, String content) {
                    Toast.makeText(LoginActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
