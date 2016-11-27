package com.atguigu.p2pinvest.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.p2pinvest.MainActivity;
import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.activity.AccountSafeActivity;
import com.atguigu.p2pinvest.activity.BarChartActivity;
import com.atguigu.p2pinvest.activity.ChongZhiActivity;
import com.atguigu.p2pinvest.activity.GestureVerifyActivity;
import com.atguigu.p2pinvest.activity.LineChartActivity;
import com.atguigu.p2pinvest.activity.LoginActivity;
import com.atguigu.p2pinvest.activity.PieChartActivity;
import com.atguigu.p2pinvest.activity.TiXianActivity;
import com.atguigu.p2pinvest.activity.UserInfoActivity;
import com.atguigu.p2pinvest.bean.User;
import com.atguigu.p2pinvest.common.BaseActivity;
import com.atguigu.p2pinvest.common.BaseFragment;
import com.atguigu.p2pinvest.utils.BitmapUtils;
import com.atguigu.p2pinvest.utils.UIUtils;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by 颜银 on 2016/11/11.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class MeFrangment extends BaseFragment {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.icon_time)
    RelativeLayout iconTime;
    @Bind(R.id.textView11)
    TextView textView11;
    @Bind(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @Bind(R.id.recharge)
    ImageView recharge;
    @Bind(R.id.withdraw)
    ImageView withdraw;
    @Bind(R.id.ll_touzi)
    TextView llTouzi;
    @Bind(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @Bind(R.id.ll_zichang)
    TextView llZichang;
    @Bind(R.id.ll_zhanquan)
    TextView llZhanquan;
    private SharedPreferences sp;

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
        sp = this.getActivity().getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        isLogin();
    }


    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.INVISIBLE);
        ivTopSetting.setVisibility(View.VISIBLE);
        tvTopName.setText("我的资产");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    public void isLogin() {
        SharedPreferences sp = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uf_acc = sp.getString("UF_ACC", "");
        if (TextUtils.isEmpty(uf_acc)) {//未登录 -- 空
            login();//登陆
        } else {//登陆过  --不为空
            doUser();//直接进入
        }
    }

    //得到了本地的登录信息，加载显示
    private void doUser() {


        //如果在本地fanx发现用户设置过手势密码，则在此时验证
        boolean isOpen = sp.getBoolean("isOpen", false);
        if(isOpen){//存在手势识别器
            ((BaseActivity)this.getActivity()).startNewActivity(GestureVerifyActivity.class, null);
        }


        //内存中获取用户信息
        User user = ((MainActivity) MeFrangment.this.getActivity()).readUser();

        //设置数据信息 --用户名
        textView11.setText(user.UF_ACC);

        //设置数据信息 --用户头像
//        Picasso.with(getActivity()).load(user.UF_AVATAR_URL).into(imageView1);
//        Log.e("TAG", "图片呢-------------" + user.UF_AVATAR_URL);

        //如果在本地存储了用户头像，则优先从本地获取
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFilesDir = this.getActivity().getExternalFilesDir(null);
            File file = new File(externalFilesDir, "icon.png");
            if (file.exists()) {
                //存在图片
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView1.setImageBitmap(bitmap);
                return;//如果内存有，就不联网下载啦，这在实际开发中是不行的，要不就下载不了更新的头像信息
            }
        }

        //设置用户头像--联网获取
        Picasso.with(getActivity()).load(user.UF_AVATAR_URL).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {

                //对Bitmap进行压缩处理
                Bitmap zoom = BitmapUtils.zoom(source, UIUtils.dp2px(62), UIUtils.dp2px(62));

                //对Bitmap进行圆形处理
                Bitmap circleBitmap = BitmapUtils.circleBitmap(zoom);

                //回收
                source.recycle();
                return circleBitmap;
            }

            @Override
            public String key() {
                return "";//此方法不能返回null.否则报异常
            }
        }).into(imageView1);


    }

    //未发现登录信息，提示用户登录的Dialog
    private void login() {
        new AlertDialog.Builder(getActivity())
                .setTitle("登陆")
                .setMessage("请先登录...")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MeFrangment.this.getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                        ((BaseActivity) (MeFrangment.this.getActivity())).startNewActivity(LoginActivity.class, null);
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .show();
    }

    //设置按钮  改变用户头像界面（包括退出）
    @OnClick(R.id.iv_top_setting)
    public void changeUserIcon(View view) {
        ((BaseActivity) this.getActivity()).startNewActivity(UserInfoActivity.class, null);
    }

    //充值按钮
    @OnClick(R.id.recharge)
    public void onRecharge(View view) {
//        Toast.makeText(getActivity(), "充值按钮", Toast.LENGTH_SHORT).show();
        ((BaseActivity) this.getActivity()).startNewActivity(ChongZhiActivity.class, null);
    }

    //提现按钮
    @OnClick(R.id.withdraw)
    public void onWithdraw(View view) {
//        Toast.makeText(getActivity(), "提现按钮", Toast.LENGTH_SHORT).show();
        ((BaseActivity) this.getActivity()).startNewActivity(TiXianActivity.class, null);
    }

    //折线图Demo
    @OnClick(R.id.ll_touzi)
    public void showLineChart(View view){
        ((BaseActivity)this.getActivity()).startNewActivity(LineChartActivity.class, null);
    }

    //柱状图Demo
    @OnClick(R.id.ll_touzi_zhiguan)
    public void showBarChart(View view){
        ((BaseActivity)this.getActivity()).startNewActivity(BarChartActivity.class, null);
    }

    //饼状图Demo
    @OnClick(R.id.ll_zichang)
    public void showPieChart(View view){
        ((BaseActivity)this.getActivity()).startNewActivity(PieChartActivity.class, null);
    }

    @OnClick(R.id.ll_zhanquan)
    public void showToggleButton(View view){
        ((BaseActivity)this.getActivity()).startNewActivity(AccountSafeActivity.class, null);
    }


}
