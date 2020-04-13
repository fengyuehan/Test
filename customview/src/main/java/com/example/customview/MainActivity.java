package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //有其他的颜色就设置侵入式
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(false).init();
        //白色就设置为白色黑字
        /*if (!(this instanceof ICancelImmersion)) {
            ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).statusBarColor(R.color.white).fitsSystemWindows(true).init();
        }*/
    }
}
