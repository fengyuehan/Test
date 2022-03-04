package com.example.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.wave.WaveView;

public class WaveActivity extends AppCompatActivity {
    private WaveView mWaveView;
    private Button mStartAnimation,mEndAnimation;
    ObjectAnimator objectAnimator;
    @SuppressLint({"WrongConstant", "ObjectAnimatorBinding"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        mWaveView = findViewById(R.id.wv);
        mStartAnimation = findViewById(R.id.btn_start);
        mEndAnimation = findViewById(R.id.btn_end);
        objectAnimator = ObjectAnimator.ofFloat(mWaveView,"TranslationX",0,1000f,0);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mStartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zzf","---------开始-------");
                //objectAnimator.start();
                mWaveView.startAnimation();
            }
        });
        mEndAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zzf","---------结束-------");
                //objectAnimator.end();
                mWaveView.cancelAnimation();
            }
        });
    }
}
