package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.customview.picker.SampleActivity;
import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mColorButton,mLinearShader,mRadialShader,mSweepShader,mBitmapShader;
    private Button mColorFilter,mLockView,mWaveVIew,mRulerView,mScroll,btn_cloud;
    private Button btn_dot,btn_text;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //有其他的颜色就设置侵入式
        //ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(false).init();
        //白色就设置为白色黑字
        if (!(this instanceof ICancelImmersion)) {
            ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).statusBarColor(R.color.white).fitsSystemWindows(true).init();
        }
        initView();
        initListener();
    }

    private void initListener() {
        mColorButton.setOnClickListener(this);
        mLinearShader.setOnClickListener(this);
        mRadialShader.setOnClickListener(this);
        mSweepShader.setOnClickListener(this);
        mBitmapShader.setOnClickListener(this);
        mColorFilter.setOnClickListener(this);
        mLockView.setOnClickListener(this);
        mWaveVIew.setOnClickListener(this);
        mRulerView.setOnClickListener(this);
        mScroll.setOnClickListener(this);
        btn_cloud.setOnClickListener(this);
        btn_dot.setOnClickListener(this);
        btn_text.setOnClickListener(this);
    }

    private void initView() {
        mColorButton = findViewById(R.id.btn);
        mLinearShader = findViewById(R.id.btn_linear_shader);
        mRadialShader = findViewById(R.id.btn_radial_shader);
        mSweepShader = findViewById(R.id.btn_sweep_shader);
        mBitmapShader = findViewById(R.id.btn_bitmap_shader);
        mColorFilter = findViewById(R.id.btn_color_filter);
        mLockView = findViewById(R.id.btn_lock_view);
        mWaveVIew = findViewById(R.id.btn_wave_view);
        mRulerView = findViewById(R.id.btn_ruler_view);
        mScroll = findViewById(R.id.btn_scroll);
        btn_cloud = findViewById(R.id.btn_cloud);
        btn_dot = findViewById(R.id.btn_dot);
        btn_text = findViewById(R.id.btn_text);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                startActivity(new Intent(MainActivity.this,ColorActivity.class));
                break;
            case R.id.btn_linear_shader:
                startActivity(new Intent(MainActivity.this,LinearGradientActivity.class));
                break;
            case R.id.btn_radial_shader:
                startActivity(new Intent(MainActivity.this,RadialGradientActivity.class));
                break;
            case R.id.btn_sweep_shader:
                startActivity(new Intent(MainActivity.this,SweepGradientActivity.class));
                break;
            case R.id.btn_bitmap_shader:
                startActivity(new Intent(MainActivity.this,BitmapShaderActivity.class));
                break;
            case R.id.btn_color_filter:
                startActivity(new Intent(MainActivity.this,ColorFliterActivity.class));
                break;
            case R.id.btn_lock_view:
                startActivity(new Intent(MainActivity.this,LockActivity.class));
                break;
            case R.id.btn_wave_view:
                startActivity(new Intent(MainActivity.this,WaveActivity.class));
                break;
            case R.id.btn_ruler_view:
                startActivity(new Intent(MainActivity.this,RulerActivity.class));
                break;
            case R.id.btn_scroll:
                startActivity(new Intent(MainActivity.this,ScrollActivity.class));
                break;
            case R.id.btn_cloud:
                startActivity(new Intent(MainActivity.this,CloudMusicLoadingActivity.class));
                break;
            case R.id.btn_dot:
                startActivity(new Intent(MainActivity.this,VerticalTextIndicatorActivity.class));
                break;
            case R.id.btn_text:
                startActivity(new Intent(MainActivity.this, SampleActivity.class));
                break;
        }
    }
}
