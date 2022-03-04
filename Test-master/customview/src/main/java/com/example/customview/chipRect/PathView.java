package com.example.customview.chipRect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * author : zhangzf
 * date   : 2021/5/17
 * desc   :
 */
public class PathView extends View {
    private Paint mPaint;
    private Path mPath1,mPath2;
    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPath1 = new Path();
        mPath2 = new Path();
    }

    /**
     * Path.Direction.CW:顺时针
     * Path.Direction.CCW ：逆时针
     * 结合setLastPoint（）这个方法会特别明显
     * @param canvas
     */

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DIFFERENCE(canvas);
        INTERSECT(canvas);
        UNION(canvas);
        XOR(canvas);
        REVERSE_DIFFERENCE(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void REVERSE_DIFFERENCE(Canvas canvas) {
        mPath1.addRect(100,700,300,900,Path.Direction.CW);
        mPath2.addCircle(300,900,100,Path.Direction.CW);
        Path path = new Path();
        path.op(mPath1,mPath2, Path.Op.REVERSE_DIFFERENCE);
        canvas.drawPath(path,mPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void XOR(Canvas canvas) {
        mPath1.addRect(400,400,700,600,Path.Direction.CW);
        mPath2.addCircle(700,600,100,Path.Direction.CW);
        Path path = new Path();
        path.op(mPath1,mPath2, Path.Op.XOR);
        canvas.drawPath(path,mPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void UNION(Canvas canvas) {
        mPath1.addRect(100,400,300,600,Path.Direction.CW);
        mPath2.addCircle(300,600,100,Path.Direction.CW);
        Path path = new Path();
        path.op(mPath1,mPath2, Path.Op.UNION);
        canvas.drawPath(path,mPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void INTERSECT(Canvas canvas) {
        mPath1.addRect(400,100,700,300,Path.Direction.CW);
        mPath2.addCircle(700,300,100,Path.Direction.CW);
        Path path = new Path();
        path.op(mPath1,mPath2, Path.Op.INTERSECT);
        canvas.drawPath(path,mPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void DIFFERENCE(Canvas canvas) {
        mPath1.addRect(100,100,300,300,Path.Direction.CW);
        mPath2.addCircle(300,250,100,Path.Direction.CW);
        Path path = new Path();
        path.op(mPath1,mPath2, Path.Op.DIFFERENCE);
        canvas.drawPath(path,mPaint);
    }
}
