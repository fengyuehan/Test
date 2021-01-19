package com.example.dialogfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity implements EditNameDialogFragment.LoginInputListener {

    private Button btn;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn =  findViewById(R.id.btn_dialog_fragment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(getApplication())
                        .setTitle("我是标题")
                        .setMessage("我是内容")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
            }
        });
        findViewById(R.id.btn_application_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showApplicationDialog();
            }
        });
        findViewById(R.id.btn_server_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMyPermission();
            }
        });
    }

    private void checkMyPermission() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);
            }
        }*/
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mHandler == null){
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (requestCode == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Settings.canDrawOverlays(MainActivity.this)) {
                            Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,MyService.class);
                            startService(intent);
                        } else {
                            // SYSTEM_ALERT_WINDOW permission not granted...
                            Toast.makeText(MainActivity.this, "未被授予权限，相关功能不可用", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        },500);

    }
    private void showApplicationDialog() {
        Dialog dialog = new AlertDialog.Builder(getApplication())
                .setTitle("我是标题")
                .setMessage("我是内容")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        /*if (isMIUIRom()){
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        }else {
            if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            } else {
                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            }
        }*/
        if (Build.VERSION.SDK_INT >= 26) {//8.0新特性
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();

        dialog.cancel();
        dialog.dismiss();
    }


    private void showEditDialog() {
        EditNameDialogFragment mEditNameDialogFragment = new EditNameDialogFragment();
        //mEditNameDialogFragment.show(getSupportFragmentManager(),"etit");
    }

    @Override
    public void onLoginInputComplete(String mUserName, String mPassWord) {
        Toast.makeText(this, "帐号：" + mUserName + ",  密码 :" + mPassWord, Toast.LENGTH_SHORT).show();
    }

    public  boolean isMIUIRom(){
        String property =getSystemProperty("ro.miui.ui.version.name");
        return !TextUtils.isEmpty(property);
    }


    public  String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return line;
    }
}
