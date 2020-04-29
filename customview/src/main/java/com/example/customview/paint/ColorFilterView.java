package com.example.customview.paint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class ColorFilterView extends View {
    /**
     * LightingColorFilter(int mul, int add)
     *  R' = R * mul.R / 0xff + add.R
     *  G' = G * mul.G / 0xff + add.G
     *  B' = B * mul.B / 0xff + add.B
     *  假设LightingColorFilter(0x00ffff, 0x003000);
     *  R' = R * 0x00 + 0x00
     *  G' = G * 0xff + 0x30
     *  B' = B * 0xff + 0x00
     */

    /**
     * ColorMatrixColorFilter
     * 其内部是一个4*5的矩阵
     * [ a, b, c, d, e,
     *   f, g, h, i, j,
     *   k, l, m, n, o,
     *   p, q, r, s, t ]
     *
     *   ColorMatrix 可以把要绘制的像素进行转换。对于颜色 [R, G, B, A] ，转换算法是这样的：
     *   R’ = a*R + b*G + c*B + d*A + e;
     *  G’ = f*R + g*G + h*B + i*A + j;
     *  B’ = k*R + l*G + m*B + n*A + o;
     *  A’ = p*R + q*G + r*B + s*A + t;
     */
    private Paint mPaint;

    public ColorFilterView(Context context) {
        this(context,null);
        init();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        ColorFilter colorFilter = new LightingColorFilter(0x00ffff,0x003000);
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation")
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        canvas.drawBitmap(bitmap,300,300,mPaint);
    }
}
