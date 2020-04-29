package com.example.customview.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RadialGradientView extends View {
    /**
     * 辐射就是从中心向周围辐射状的渐变
     * @param context
     */
    private Paint radialPaint1,radialPaint2,radialPaint3;

    public RadialGradientView(Context context) {
        this(context,null);
        init();
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        radialPaint1 = new Paint();
        radialPaint2 = new Paint();
        radialPaint3 = new Paint();
        Shader shader = new RadialGradient(300, 300, 100, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        radialPaint1.setShader(shader);
        Shader shader1 = new RadialGradient(300, 300, 100, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);
        radialPaint2.setShader(shader1);
        Shader shader2 = new RadialGradient(300, 300, 100, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
        radialPaint3.setShader(shader2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(300, 300, 200, radialPaint1);
        canvas.drawCircle(300, 800, 200, radialPaint2);
        canvas.drawCircle(300, 1300, 200, radialPaint3);
    }
}
