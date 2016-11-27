package com.atguigu.p2pinvest.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.p2pinvest.MainActivity;
import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.bean.UpdateInfo;
import com.atguigu.p2pinvest.common.ActivityManager;
import com.atguigu.p2pinvest.common.AppNetConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends Activity {

    private static final int TO_MAIN_ACTIVITY = 1;
    private static final int CONNECT_NET_FAILYRE = 2;
    private static final int CONNECT_NET_SUCCESS = 3;
    private static final int DOWN_SUCCESS = 4;
    private static final int WHAT_DOWNLOAD_FAIL = 5;
    //版本号
    @Bind(R.id.tv_welcome_version)
    TextView tvWelcomeVersion;

    @Bind(R.id.rl_welcome)
    RelativeLayout rlWelcome;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case TO_MAIN_ACTIVITY://回到主页面
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    break;
                case CONNECT_NET_FAILYRE://联网失败
                    Toast.makeText(WelcomeActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    toMain();
                    break;
                case CONNECT_NET_SUCCESS://联网成功
                    //校验版本
                    String version = getVersion();
                    if (version.equals(updateInfo.version)) {//版本相同，不要更新
                        toMain();
                    } else {//版本不同，需要更新
                        showDownloadDialog();
                    }
                    break;
                case WHAT_DOWNLOAD_FAIL:
                    Toast.makeText(WelcomeActivity.this, "联网下载失败", Toast.LENGTH_SHORT).show();
                    toMain();
                    break;
                case DOWN_SUCCESS://安装应用
                    installApk();
                    break;
            }
        }
    };

    //安装应用
    private void installApk() {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setData(Uri.parse("file:" + apkFile.getAbsolutePath()));
        startActivity(intent);
    }

    private File apkFile;
    private ProgressDialog dialog;

    //联网下载最新APK提示
    private void showDownloadDialog() {
        Log.e("TAG", "下载提示--确定下载最新版本吗");
        new AlertDialog.Builder(this)
                .setTitle("确定下载最新版本吗")
                .setMessage(updateInfo.desc)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("TAG", "55555555555555");
                        showDownLoad();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(WelcomeActivity.this, "取消下载最新版本", Toast.LENGTH_SHORT).show();
                        toMain();
                    }
                })
                .show();
    }

    /**
     * 联网下载指定url地址对应的apk文件
     * 1.提供ProgressDialog
     * 2.提供本地的存储文件
     * 3.联网下载数据
     * 4.安装
     */
    private void showDownLoad() {
        //1.提供ProgressDialog
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//水平显示
        dialog.setCancelable(false);
        dialog.show();

        //2.提供本地的存储文件:sd卡路径1
        String filePath = this.getExternalFilesDir(null) + "/update_app.apk";
        apkFile = new File(filePath);

        //3.联网下载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "55555555555555");
                try {
                    downloadAPk();

                    //发消息提示安装
                    handler.sendEmptyMessage(DOWN_SUCCESS);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    //下载最新apk
    private void downloadAPk() throws Exception {
        Log.e("TAG", "666666666666666666");


        FileOutputStream fos = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            fos = new FileOutputStream(apkFile);

            String path = updateInfo.apkUrl;
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            conn.connect();

            if (conn.getResponseCode() == 200) {
                Log.e("TAG", "777777777777777777");
                is = conn.getInputStream();

                //设置进度条的长度
                dialog.setMax(conn.getContentLength());

                byte[] bytes = new byte[1024];

                int len ;
                while((len = is.read(bytes)) != -1){
                    fos.write(bytes,0,len);

                    //设置进度条增加
                    dialog.incrementProgressBy(len);
                    Thread.sleep(2);
                }

            } else {//联网失败
                handler.sendEmptyMessage(WHAT_DOWNLOAD_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关闭资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                //关闭连接
                conn.disconnect();
            }

        }
    }

    private long startTime;
    private UpdateInfo updateInfo;

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

        Log.e("TAG", "1111111111111111111");

        //判断是否联网
        initData();
    }

    private void initData() {
        //获取系统当前的时间
        startTime = System.currentTimeMillis();
        //检查手机是否联网
        boolean connect = isConnected();

        Log.e("TAG", "2222222222----" + connect);

        if (connect) {//联网成功
            String upDataUrl = AppNetConfig.UPDATE;//获取联网请求的路径

            Log.e("TAG", "444444444" +upDataUrl );
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(upDataUrl, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(String content) {
                    Log.e("TAG", "3333333333" + content.toString());
                    //使用fastjson解析json数据
                    updateInfo = JSON.parseObject(content, UpdateInfo.class);
                    handler.sendEmptyMessage(CONNECT_NET_SUCCESS);
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    handler.sendEmptyMessage(CONNECT_NET_FAILYRE);

                }
            });

        } else {//联网失败
            Toast.makeText(WelcomeActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            toMain();
        }
    }

    //回到主页面
    private void toMain() {
        long currentTimeMillis = System.currentTimeMillis();

        //计算开始联网到现在的时间差
        long duringTime = 3000 - (currentTimeMillis - startTime);
        if (duringTime < 0) {
            duringTime = 0;
        }
        handler.sendEmptyMessageDelayed(TO_MAIN_ACTIVITY, duringTime);
    }

    private boolean isConnected() {
        boolean connected = false;

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }
        return connected;
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
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                //销毁当前页面
//                finish();
//            }
//        }, 3000);

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

    public String getVersion() {
        String version = "未知版本";
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace(); //如果找不到对应的应用包信息, 就返回"未知版本"
        }
        return version;
    }
}
