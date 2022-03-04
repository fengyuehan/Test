package com.example.customview.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class BitmapShaderView extends View {
    private Paint radialPaint1,radialPaint2,radialPaint3;
    public BitmapShaderView(Context context) {
        this(context,null);
        init();
    }

    public BitmapShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        radialPaint1 = new Paint();
        radialPaint2 = new Paint();
        radialPaint3 = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        radialPaint1.setShader(shader);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        radialPaint2.setShader(shader1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        radialPaint3.setShader(shader2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(170, 200, 200, radialPaint1);
        canvas.drawCircle(300, 800, 200, radialPaint2);
        canvas.drawCircle(300, 1300, 200, radialPaint3);
    }
}
