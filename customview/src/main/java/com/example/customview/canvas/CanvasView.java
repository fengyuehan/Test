package com.example.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author : zhangzf
 * date   : 2021/3/22
 * desc   :
 */
public class CanvasView extends View {
    private Paint mPaint;

    public CanvasView(Context context) {
        this(context,null);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*canvas.save();
        canvas.translate(200,200);
        canvas.save();
        canvas.translate(200,200);
        canvas.save();//第三次保存
        canvas.translate(200,200);

        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,100,100,mPaint);
        canvas.restore();
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,100,100,mPaint);
        canvas.restore();
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0,0,100,100,mPaint);*/

        canvas.save();//第一次保存，count2
        canvas.translate(200,200);
        canvas.save();// 保存第二次，count3
        canvas.translate(200,200);
        canvas.save();//第三次保存
        canvas.translate(200,200);
        canvas.save();//第四次保存
        canvas.translate(200,200);
        canvas.save();//第五次保存
        canvas.translate(200,200);
        canvas.save();
        //直接4，就可以回到画蓝色矩形位置
        canvas.restoreToCount(4);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0,100,100,mPaint);
    }
}
