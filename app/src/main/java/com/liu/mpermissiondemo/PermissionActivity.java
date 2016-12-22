package com.liu.mpermissiondemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 测试Android6.0权限
 * 以Activity为例，Fragment中的流程一样
 * Created by liu on 2016/12/21.
 */

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;
    private Button apply_for_permission_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initView();
        setListener();
    }

    private void initView() {
        apply_for_permission_btn = (Button) findViewById(R.id.apply_for_permission_btn);
    }

    private void setListener() {
        apply_for_permission_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.apply_for_permission_btn:
                requestPermission();
                break;
            default:
                break;
        }
    }

    //请求权限
    private void requestPermission(){
        //检测是否授予READ_PHONE_STATE权限，方法返回值为PackageManager.PERMISSION_DENIED或者PackageManager.PERMISSION_GRANTED。当返回DENIED就需要进行申请授权了
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //这个方法会在用户拒绝过一次该权限申请调用
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                //已经被拒绝了一次了，告诉用户为什么申请该权限，说不定就同意了呢
                Toast.makeText(this,"被拒绝过了，让我通过吧",Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this).setMessage("给我权限吧").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(PermissionActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            } else {
                //申请权限
                Toast.makeText(this,"被拒绝了",Toast.LENGTH_SHORT).show();
                startAppSetting();
            }


        } else {
            readPhoneState();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length>0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readPhoneState();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_PHONE_STATE)){
                        Toast.makeText(this, "权限申请失败", Toast.LENGTH_SHORT).show();
                        //提醒用户去设置界面
                       startAppSetting();
                    }

                }
            }

        }
    }

    private void readPhoneState() {
        Toast.makeText(this, "读取手机的状态", Toast.LENGTH_SHORT).show();
    }

    private void startAppSetting(){
        Intent i = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");

        String pkg = "com.android.settings";
        String cls = "com.android.settings.applications.InstalledAppDetails";

        i.setComponent(new ComponentName(pkg, cls));
        i.setData(Uri.parse("package:" + getPackageName()));
        startActivity(i);
    }
}
