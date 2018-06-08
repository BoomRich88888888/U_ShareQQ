package com.example.u_shareqq;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UMShareListener {
    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};

    private Button mBianBan;
    private Button mNoBianBan;
    private Button mQQLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if(Build.VERSION.SDK_INT>=23){
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
    }

    private void initView() {
        mBianBan = (Button) findViewById(R.id.mBianBan);
        mNoBianBan = (Button) findViewById(R.id.mNoBianBan);
        mQQLogin = (Button) findViewById(R.id.mQQLogin);

        mBianBan.setOnClickListener(this);
        mNoBianBan.setOnClickListener(this);
        mQQLogin.setOnClickListener(this);
    }
    //点击则分享到QQ
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBianBan:
                UMImage umImage = new UMImage(MainActivity.this, R.drawable.umeng_socialize_qq);
                new ShareAction(MainActivity.this)
                        .withMedia(umImage)
                        .setDisplayList(SHARE_MEDIA.QQ)
                        .setCallback(MainActivity.this)
                        .open();
                break;
            case R.id.mNoBianBan:
                UMImage umImage1 = new UMImage(MainActivity.this, R.drawable.umeng_socialize_qzone);
                new ShareAction(MainActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withMedia(umImage1)
                        .setCallback(MainActivity.this)//回调监听器
                        .share();
                break;
            case R.id.mQQLogin:
                UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        //开始登陆
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        //登录成功
                        String uid = map.get("uid");
                        String name = map.get("name");
                        String gender = map.get("gender");
                        String iconurl = map.get("iconurl");
                        Toast.makeText(MainActivity.this, uid+"-"+name+"-"+gender+"-"+iconurl, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        //登录错误
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        //取消登录
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            int i1 = ContextCompat.checkSelfPermission(this, permission);
            if (i1!=RESULT_OK){
                return;
            }
        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Toast.makeText(this, "请求开始", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Toast.makeText(this, "请求结果", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "请求错误"+throwable.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Toast.makeText(this, "请求取消", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
