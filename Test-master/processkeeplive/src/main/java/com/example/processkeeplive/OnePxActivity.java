package com.example.processkeeplive;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * author : zhangzf
 * date   : 2021/1/21
 * desc   :使用静态注册的方式，没有效果，使用动态注册方式,不能成功跳转。
 */
public class OnePxActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.e("zzf", "--onCreate--->OnePxActivity");
        Window window = getWindow();
        //设置Activity处于左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams attr = window.getAttributes();
        //设置宽高都是1px
        attr.width = 1;
        attr.height = 1;
        //设置坐标
        attr.x = 0;
        attr.y = 0;
        window.setAttributes(attr);
        KeepManager.getManager().setKeepActivity(this);
        Log.e("zzf", "--onCreate--->OnePxActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zzf","onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzf","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzf","onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzf","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzf","onResume");
    }
}
