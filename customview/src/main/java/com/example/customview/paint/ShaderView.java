package com.example.customview.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShaderView extends View {
    /**
     *Shader 主要有5种
     * LinearGradient：线性渐变
     * RadialGradient ：辐射渐变
     * SweepGradient ：扫描渐变
     * BitmapShader：
     * ComposeShader ：组合着色器
     * @param context
     */

    /**
     * TileMode:一共有三种
     * CLAMP：会在端点之外延续端点处的颜色
     * MIRROR ：镜像模式
     * REPEAT ：重复模式
     * @param context
     */
    private Paint linearPaint1,linearPaint2,linearPaint3;

    public ShaderView(Context context) {
        this(context,null);
        init();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linearPaint1 = new Paint();
        linearPaint2 = new Paint();
        linearPaint3 = new Paint();
        Shader shader = new LinearGradient(100,100,500,500, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        linearPaint1.setShader(shader);
        Shader shader1 = new LinearGradient(100,100,500,500, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
        linearPaint2.setShader(shader1);
        Shader shader2 = new LinearGradient(100,100,500,500, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
        linearPaint3.setShader(shader2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300, 300, 200, linearPaint1);
        canvas.drawCircle(300, 800, 200, linearPaint2);
        canvas.drawCircle(300, 1300, 200, linearPaint3);
    }
}
