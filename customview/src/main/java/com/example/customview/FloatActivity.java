package com.example.customview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.floatbutton.Callback;
import com.example.customview.floatbutton.FloatButtonLayout;

/**
 * author : zhangzf
 * date   : 2021/1/13
 * desc   :
 */
public class FloatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_button_layout);
        FloatButtonLayout floatButton = findViewById(R.id.float_button_layout);
        //设置点击事件
        floatButton.setCallback(new Callback() {
            @Override
            public void onClickFloatButton() {
                startActivity(new Intent(FloatActivity.this, MainActivity.class));
            }
        });
    }
}
