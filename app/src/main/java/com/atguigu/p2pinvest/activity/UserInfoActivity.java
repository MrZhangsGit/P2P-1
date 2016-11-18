package com.atguigu.p2pinvest.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.p2pinvest.MainActivity;
import com.atguigu.p2pinvest.R;
import com.atguigu.p2pinvest.common.BaseActivity;
import com.atguigu.p2pinvest.utils.BitmapUtils;
import com.atguigu.p2pinvest.utils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_name)
    TextView tvTopName;
    @Bind(R.id.iv_top_setting)
    ImageView ivTopSetting;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.tv_userinfo_changeicon)
    TextView tvUserinfoChangeIcon;
    @Bind(R.id.loginout)
    Button loginout;
    private int CAMERA = 100;
    private int PICTURE = 200;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //开始显示上次修改信息，没有
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File externalFilesDir = this.getExternalFilesDir(null);
            File file = new File(externalFilesDir,"icon.png");
            if(file.exists()){
                //存在图片
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView1.setImageBitmap(bitmap);
                return;//如果内存有，就不联网下载啦，这在实际开发中是不行的，要不就下载不了更新的头像信息
            }
        }
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        tvTopName.setText("用户信息");
        ivTopSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }


    //点击返回按钮
    @OnClick(R.id.iv_top_back)
    public void setIvTopBack(View view){

        //销毁当前Activity,重新进入
        this.removeAllActivity();
        this.startNewActivity(MainActivity.class, null);
    }

    //点击退出登录按钮
    @OnClick(R.id.loginout)
    public void logout(View view){
        //推出之前，清空内存信息
        SharedPreferences sp = this.getSharedPreferences("user_info",MODE_PRIVATE);
        sp.edit().clear().commit();//清空内存

        //清楚头像信息
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File externalFilesDir = this.getExternalFilesDir(null);
            File file = new File(externalFilesDir,"icon.png");
            if(file.exists()){
                //存在图片
                file.delete();//删除文件
            }
        }

        //销毁当前Activity，重新进入
        this.removeAllActivity();
        this.startNewActivity(MainActivity.class,null);
    }

    //点击更换头头像按钮
    @OnClick(R.id.tv_userinfo_changeicon)
    public void changeIcon(View view) {
        new AlertDialog.Builder(this)
                .setTitle("选择路径")
                .setItems(new String[]{"相机", "图库"},new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//点击回调
                        switch (which) {
                            case 0://相机
                                //打开系统拍照程序，选择拍照图片
                                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(camera, CAMERA);
                                break;
                            case 1://图库
                                //打开系统图库程序，选择图片
                                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(picture, PICTURE);
                                break;
                        }
                    }
                })
                .setCancelable(true)
                .show();
    }

    /**
     * 点击回调，保存头像图片的方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String path = getCacheDir() + "/tx.png";

        if(requestCode == CAMERA && resultCode == RESULT_OK && data!=null){//相机回调
            //拍照
            Bundle bundle = data.getExtras();
            // 获取相机返回的数据，并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");

            //先执行压缩图片
            Bitmap zoomBitmap = BitmapUtils.zoom(bitmap, UIUtils.dp2px(62), UIUtils.dp2px(62));
            //设置圆形--剪裁圆形图片
            Bitmap circleBitmap = BitmapUtils.circleBitmap(zoomBitmap);

            //设置图片   注意:真实项目中我们在这将图片保存到服务器中
            imageView1.setImageBitmap(circleBitmap);

            //保存到内存中
            try {
                saveImage(circleBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }else if(requestCode == PICTURE && resultCode == RESULT_OK && data!=null){//相册回调
            Uri selectedImage = data.getData();

            //这里返回的uri情况就有点多了
            //**:在4.4.2之前返回的uri是:content://media/external/images/media/3951或者file:
            // ....在4.4.2返回的是content://com.android.providers.media.documents/document/image:3951或者
            //总结：uri的组成，eg:content://com.example.project:200/folder/subfolder/etc
            //content:--->"scheme"
            //com.example.project:200-->"host":"port"--->"authority"[主机地址+端口(省略) =authority]
            //folder/subfolder/etc-->"path" 路径部分
            //android各个不同的系统版本,对于获取外部存储上的资源，返回的Uri对象都可能各不一样,所以要保证无论是哪个系统版本都能正确获取到图片资源的话
            //就需要针对各种情况进行一个处理了
            String pathResult = getPath(selectedImage);

            if(!TextUtils.isEmpty(path)){//路径存在-->文件存在

                Bitmap bitmap = BitmapFactory.decodeFile(pathResult);
                //先执行压缩图片
                Bitmap zoomBitmap = BitmapUtils.zoom(bitmap, UIUtils.dp2px(62), UIUtils.dp2px(62));
                //设置圆形--剪裁圆形图片
                Bitmap circleBitmap = BitmapUtils.circleBitmap(zoomBitmap);

                try {
                    FileOutputStream fos = new FileOutputStream(path);
                    //保存到内存中 保存路径为  path
                    circleBitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                    //真实项目当中，是需要上传到服务器的..这步我们就不做了。
                    imageView1.setImageBitmap(circleBitmap);

                    //将图片保存在本地
                    saveImage(circleBitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //将修改后的图片保存在本地存储中：storage/sdcard/Android/data/应用包名/files/xxx.png
    private void saveImage(Bitmap circleBitmap) throws FileNotFoundException {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File externalFilesDir = this.getExternalFilesDir(null);
            File file = new File(externalFilesDir, "icon.png");
            //将Bitmap持久化
            circleBitmap.compress(Bitmap.CompressFormat.PNG,100,new FileOutputStream(file));
        }
    }



    /**
     * 根据系统相册选择的文件获取路径
     *
     * @param uri
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        //高于4.4.2的版本
        if (sdkVersion >= 19) {
            Log.e("TAG", "uri auth: " + uri.getAuthority());
            if (isExternalStorageDocument(uri)) {
                String docId = DocumentsContract.getDocumentId(uri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(this, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(this, contentUri, selection, selectionArgs);
            } else if (isMedia(uri)) {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor actualimagecursor = this.managedQuery(uri, proj, null, null, null);
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                return actualimagecursor.getString(actual_image_column_index);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(this, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * uri路径查询字段
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isMedia(Uri uri) {
        return "media".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
