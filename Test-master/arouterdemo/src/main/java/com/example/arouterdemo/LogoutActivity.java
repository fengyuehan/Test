package com.example.arouterdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/main/LogoutActivity")
public class LogoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        Log.e("zzf","--------onCreate--------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzf","--------onStart--------");
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
