package com.example.customview;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.shape.ShapeCreator;

/**
 * author : zhangzf
 * date   : 2021/4/2
 * desc   :
 */
public class ShapeActivity extends AppCompatActivity {
    private TextView tv;
    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        tv = findViewById(R.id.tv);
        ll = findViewById(R.id.ll_bg);
        /*ShapeCreator.create()
                .setCornerRadius(10)
                .setSolidColor(Color.GRAY)
                .setStrokeColor(Color.CYAN)
                .setStrokeWidth(2)
                .into(tv);*/
        ShapeCreator.create()
                .setCornerRadius(10)
                //.setSolidColor(Color.GRAY)
                .setStrokeColor(Color.CYAN)
                .setStrokeWidth(2)
                .setStrokeDashWidth(10)
                .setStrokeDashGap(10)
                .into(tv);
        ShapeCreator.create()
                .setCornerRadius(10)
                .setSolidColor(Color.CYAN)
                //.setStrokeColor(Color.CYAN)
                //.setStrokeWidth(2)
                //.setStrokeDashWidth(10)
                //.setStrokeDashGap(10)
                .into(ll);
    }
}
