package com.example.customview.layer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author : zhangzf
 * date   : 2021/5/4
 * desc   :
 */
public class XfermodeView extends View {
    private int width = 400;
    private int height = 400;
    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint paint;


    public XfermodeView(Context context) {
        this(context,null);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        srcBmp = createSrc(width,height);
        dstBmp = createDst(width,height);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        int id = canvas.saveLayer(0,0,width * 2,height * 2,paint,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(srcBmp,0,0,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(dstBmp,width/2,height/2,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(id);
    }

    private Bitmap createDst(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF66AAFF);
        canvas.drawRect(0,0,width,height,paint);
        return bitmap;
    }

    private Bitmap createSrc(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFFCC44);
        canvas.drawOval(new RectF(0,0,width,height),paint);
        return bitmap;
    }
}
