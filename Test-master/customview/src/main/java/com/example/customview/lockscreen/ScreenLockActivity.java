package com.example.customview.lockscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.R;

public class ScreenLockActivity extends AppCompatActivity {

    SlideLockView lockView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_lock);
        lockView = findViewById(R.id.slide_rail);
        lockView.setCallback(new SlideLockView.Callback() {
            @Override
            public void onUnlock() {
                startActivity(new Intent(ScreenLockActivity.this,HomeActivity.class));
                finish();
            }
        });
    }
}
