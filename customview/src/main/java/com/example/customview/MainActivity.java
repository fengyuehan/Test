package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.customview.lockscreen.HomeActivity;
import com.example.customview.picker.SampleActivity;
import com.gyf.immersionbar.ImmersionBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mColorButton,mLinearShader,mRadialShader,mSweepShader,mBitmapShader;
    private Button mColorFilter,mLockView,mWaveVIew,mRulerView,mScroll,btn_cloud;
    private Button btn_dot,btn_text,btn_draw_text,btn_draw_more_text,btn_lock_screen,btn_red_envelopes,btn_image_autentication,btn_travel_people;
    private Button btn_canvas,btn_ship,btn_matrix,btn_pen,btn_mosaic,btn_gua,btn_shape;
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
        btn_draw_text.setOnClickListener(this);
        btn_draw_more_text.setOnClickListener(this);
        btn_lock_screen.setOnClickListener(this);
        btn_red_envelopes.setOnClickListener(this);
        btn_image_autentication.setOnClickListener(this);
        btn_travel_people.setOnClickListener(this);
        btn_canvas.setOnClickListener(this);
        btn_ship.setOnClickListener(this);
        btn_matrix.setOnClickListener(this);
        btn_pen.setOnClickListener(this);
        btn_mosaic.setOnClickListener(this);
        btn_gua.setOnClickListener(this);
        btn_shape.setOnClickListener(this);
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
        btn_draw_text = findViewById(R.id.btn_draw_text);
        btn_draw_more_text = findViewById(R.id.btn_draw_more_text);
        btn_lock_screen = findViewById(R.id.btn_lock_screen);
        btn_red_envelopes = findViewById(R.id.btn_red_envelopes);
        btn_image_autentication = findViewById(R.id.btn_image_autentication);
        btn_travel_people = findViewById(R.id.btn_travel_people);
        btn_canvas = findViewById(R.id.btn_canvas);
        btn_ship = findViewById(R.id.btn_ship);
        btn_matrix = findViewById(R.id.btn_matrix);
        btn_pen = findViewById(R.id.btn_pen);
        btn_mosaic = findViewById(R.id.btn_mosaic);
        btn_gua = findViewById(R.id.btn_gua);
        btn_shape = findViewById(R.id.btn_shape);
    }

    @SuppressLint("NonConstantResourceId")
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
            case R.id.btn_draw_text:
                startActivity(new Intent(MainActivity.this,DrawTextActivity.class));
                break;
            case R.id.btn_draw_more_text:
                startActivity(new Intent(MainActivity.this,DrawMoreLineActivity.class));
                break;
            case R.id.btn_lock_screen:
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                break;
            case R.id.btn_red_envelopes:

                break;
            case R.id.btn_image_autentication:
                startActivity(new Intent(MainActivity.this,ImageAuthenticationActivity.class));
                break;
            case R.id.btn_travel_people:
                startActivity(new Intent(MainActivity.this,TravelPeopleActivity.class));
                break;
            case R.id.btn_canvas:
                startActivity(new Intent(MainActivity.this,CanvasActivity.class));
                break;
            case R.id.btn_ship:
                startActivity(new Intent(MainActivity.this,ShipActivity.class));
                break;
            case R.id.btn_matrix:
                startActivity(new Intent(MainActivity.this,MatrixActivity.class));
                break;
            case R.id.btn_pen:
                startActivity(new Intent(MainActivity.this,DoodleActivity.class));
                break;
            case R.id.btn_mosaic:
                startActivity(new Intent(MainActivity.this,MosaicActivity.class));
                break;
            case R.id.btn_gua:
                startActivity(new Intent(MainActivity.this,GuaGuaActivity.class));
                break;
            case R.id.btn_shape:
                startActivity(new Intent(MainActivity.this,ShapeActivity.class));
                break;
        }
    }
}
