package com.example.customview;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.lockview.Listener;
import com.example.customview.lockview.LockView;

public class LockActivity extends AppCompatActivity {
    private LockView lockView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        init();
        lockView.setRightPsw("156258");
        lockView.setmListener(new Listener() {
            @Override
            public void onInputOK() {
                Toast.makeText(LockActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInputError() {
                Toast.makeText(LockActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        lockView = findViewById(R.id.lv);
    }
}
