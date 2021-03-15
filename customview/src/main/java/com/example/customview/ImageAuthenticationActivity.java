package com.example.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.ImageAuthentication.ImageAuthenticationView;

/**
 * author : zhangzf
 * date   : 2021/3/8
 * desc   :
 */
public class ImageAuthenticationActivity extends AppCompatActivity {
    private ImageAuthenticationView dy_v;
    private SeekBar sb_dy;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_authentication);
        dy_v = findViewById(R.id.dy_v);
        sb_dy = findViewById(R.id.sb_dy);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dy_v.reSet();
            }
        });

        sb_dy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //设置滑块移动距离
                dy_v.setUnitMoveDistance(dy_v.getAverageDistance(seekBar.getMax()) * progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //验证是否拼接成功
                dy_v.testPuzzle();
            }
        });
    }
}
