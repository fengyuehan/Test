package com.example.arouterdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

@Route(path = "/main/login")
public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("zzf","跳转过来了");
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzf","--------onResume--------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzf","--------onPause--------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzf","--------onStop--------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zzf","--------onDestroy--------");
    }
}
