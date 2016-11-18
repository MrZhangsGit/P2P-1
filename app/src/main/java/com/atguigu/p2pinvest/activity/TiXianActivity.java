package com.atguigu.p2pinvest.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseActivity;
import com.atguigu.p2pinvest.utils.UIUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class TiXianActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.account_zhifubao)
    TextView accountZhifubao;
    @Bind(R.id.select_bank)
    RelativeLayout selectBank;
    @Bind(R.id.chongzhi_text)
    TextView chongzhiText;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.input_money)
    EditText inputMoney;
    @Bind(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.btn_tixian)
    Button btnTixian;

    @Override
    protected void initListener() {
        btnTixian.setClickable(false);
        inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("TAG", "ChongZhiActivity beforeTextChanged()");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("TAG", "ChongZhiActivity onTextChanged()");
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.i("TAG", "ChongZhiActivity afterTextChanged()");
                String inputMoner = inputMoney.getText().toString().trim();
                if(TextUtils.isEmpty(inputMoner)){
                    //为空--设置不可点击--按钮背景颜色
                    btnTixian.setClickable(false);
                    btnTixian.setBackgroundResource(R.drawable.btn_023);
                }else {
                    //不为空---设置可点击---按钮背景颜色
                    btnTixian.setClickable(true);
                    btnTixian.setBackgroundResource(R.drawable.btn_01);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopName.setText("提现");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    //提现按钮点击事件
    @OnClick(R.id.btn_tixian)
    public void withDraw(View view) {
        //获取提现信息
        String inputMoner = inputMoney.getText().toString().trim();
        //真实项目发送信息到服务器后台完成的提现请求
        Toast.makeText(TiXianActivity.this, "您的提现请求发送成功，提现金额为" + inputMoner, Toast.LENGTH_SHORT).show();

        //完事结束当前界面  延迟2S,显示提示信息
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeCurrentActivity();//销毁当前Activity
            }
        },2000);

    }

    //返回按钮的点击事件
    @OnClick(R.id.iv_top_back)
    public void back(View view) {
        this.removeCurrentActivity();//销毁当前页面
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ti_xian;
    }

}
