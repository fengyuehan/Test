package com.example.customview.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.customview.R;

/**
 * author : zhangzf
 * date   : 2021/5/14
 * desc   :
 */
public class ColorTextView extends androidx.appcompat.widget.AppCompatTextView {

    private Paint mOriginPaint,mChangePaint,mBarOriginPaint,mBarChangePaint;
    private float mPercent = 0.0f;
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public ColorTextView(Context context) {
        this(context,null);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTextView);
        int originColor = typedArray.getColor(R.styleable.ColorTextView_originColor,getTextColors().getDefaultColor());
        int changeColor = typedArray.getColor(R.styleable.ColorTextView_changeColor,getTextColors().getDefaultColor());
        int barOriginColor = typedArray.getColor(R.styleable.ColorTextView_baroriginColor,getTextColors().getDefaultColor());
        int barChangeColor = typedArray.getColor(R.styleable.ColorTextView_barchangeColor,getTextColors().getDefaultColor());

        mOriginPaint = getPaintByColor(originColor);
        mChangePaint = getPaintByColor(changeColor);
        mBarOriginPaint = getPaintByColor(barOriginColor);
        mBarChangePaint = getPaintByColor(barChangeColor);
        typedArray.recycle();
    }

    private Paint getPaintByColor(int originColor) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(originColor);
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //裁剪区域
        mBarOriginPaint.setStrokeWidth(3);
        mBarChangePaint.setStrokeWidth(3);
        int middle = (int) (mPercent * getWidth());
        if (mDirection == Direction.LEFT_TO_RIGHT){
            //绘制不变色的
            //左边是红色，右边是黑色
            drawText(canvas,mChangePaint,0,middle);
            //绘制变色的
            drawText(canvas,mOriginPaint,middle,getWidth());
            //绘制线
            drawLine(canvas,mBarChangePaint,0,middle);
            drawLine(canvas,mBarOriginPaint,middle,getWidth());
        }else {
            //绘制不变色的
            drawText(canvas,mChangePaint,getWidth() - middle,getWidth());
            //绘制变色的
            drawText(canvas,mOriginPaint,0,getWidth()-middle);
            drawLine(canvas,mBarChangePaint,getWidth() - middle,getWidth());
            drawLine(canvas,mBarOriginPaint,0,getWidth()-middle);
        }

    }

    private void drawLine(Canvas canvas, Paint mBarChangePaint, int start, int end) {
        canvas.save();
        Rect rect = new Rect(start,0,end,getHeight());
        canvas.clipRect(rect);
        String text = getText().toString();
        Rect bounds = new Rect();
        mBarChangePaint.getTextBounds(text,0,text.length(),bounds);
        int width = bounds.width();
        int height = bounds.height();
        canvas.drawLine(getWidth()/2 - width/2 ,getHeight()/2 + height/2,getWidth()/2 + width/2 ,getHeight()/2 + height/2,mBarChangePaint);
        canvas.restore();
    }

    /**
     * 画文字
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(Canvas canvas,Paint paint,int start,int end){
        canvas.save();
        Rect rect = new Rect(start,0,end,getHeight());
        //裁剪画布
        canvas.clipRect(rect);

        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);
        int x= getWidth()/2 - bounds.width()/2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) /2 -fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(text,x,baseline,paint);
        canvas.restore();
    }

    public enum Direction{
        LEFT_TO_RIGHT,RIGHT_TO_LEFT
    }

    public void setDirection(Direction direction){
        this.mDirection = direction;
    }

    public void setCurrentProgress(float progress){
        this.mPercent = progress;
        invalidate();
    }

    public void setChangeColor(int changeColor){
        this.mChangePaint.setColor(changeColor);
    }

    public void setOriginColor(int originColor){
        this.mOriginPaint.setColor(originColor);
    }
}
