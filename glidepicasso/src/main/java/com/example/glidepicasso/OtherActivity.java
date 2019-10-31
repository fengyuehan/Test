package com.example.glidepicasso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class OtherActivity extends AppCompatActivity {
    private final static String TAG = "ZZF";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"OtherActivity-----onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        Log.e(TAG,"OtherActivity-----onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"OtherActivity-----onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG,"OtherActivity-----onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG,"OtherActivity-----onCreate");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG,"OtherActivity-----onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG,"OtherActivity-----onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}
