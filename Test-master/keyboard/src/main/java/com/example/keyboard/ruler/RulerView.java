package com.example.keyboard.ruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class RulerView extends View {
    private static final int MIN_POSITION = 20;
    private static final int MAX_POSITION = 1200;
    private float position = 20;
    private int max = 1080;
    private int min = 870;

    private Paint drawLinePaint;
    private Paint drawTextPaint;
    private Paint drawRulerPaint;

    private OnMoveActionListener mMove = null;

    public RulerView(Context context) {
        super(context);
        initView();
    }

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        drawLinePaint = new Paint();
        drawLinePaint.setAntiAlias(true);
        drawLinePaint.setColor(Color.parseColor("#808080"));
        drawLinePaint.setStrokeWidth(1);
        drawLinePaint.setStyle(Paint.Style.STROKE);

        drawTextPaint = new Paint();
        drawTextPaint.setAntiAlias(true);
        drawTextPaint.setColor(Color.parseColor("#808080"));
        drawTextPaint.setStyle(Paint.Style.FILL);
        drawTextPaint.setStrokeWidth(2);
        drawTextPaint.setTextSize(24);

        drawRulerPaint = new Paint();
        drawRulerPaint.setAntiAlias(true);
        drawRulerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        drawRulerPaint.setColor(Color.parseColor("#FF3A30"));
        drawRulerPaint.setStrokeWidth(3);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(setMeasureWidth(widthMeasureSpec), setMeasureHeight(heightMeasureSpec));
    }

    private int setMeasureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = Integer.MAX_VALUE;
        switch (mode) {
            case MeasureSpec.AT_MOST:
                size = Math.min(result, size);
                break;
            case MeasureSpec.EXACTLY:
                break;
            default:
                size = result;
                break;
        }
        return size;
    }

    private int setMeasureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = Integer.MAX_VALUE;
        switch (mode) {
            case MeasureSpec.AT_MOST:
                size = Math.min(result, size);
                break;
            case MeasureSpec.EXACTLY:
                break;
            default:
                size = result;
                break;
        }
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        for (int i = min; i < max; i++) {
            if (i % 10 == 0) {
                canvas.drawLine(20, 0, 20, 140, drawLinePaint);

                String text = i / 10 + "";
                Rect rect = new Rect();
                float textWidth = drawTextPaint.measureText(text);
                drawTextPaint.getTextBounds(text, 0, text.length(), rect);
                if (i / 10 % 2 == 1 && i / 10 != 107) {
                    canvas.drawText(text, 20 - textWidth / 2, 146 + rect.height(), drawTextPaint);
                }
                if (i / 10 == 108) {
                    canvas.drawText(text, 20 - textWidth / 2, 146 + rect.height(), drawTextPaint);
                }
            } else if (i % 5 == 0) {
                canvas.drawLine(20, 30, 20, 110, drawLinePaint);
            } else {
                canvas.drawLine(20, 54, 20, 86, drawLinePaint);
            }
            canvas.translate((float) 8, 0);
        }
        canvas.restore();
        canvas.drawLine(position, 0, position, 140, drawRulerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                if (x < MIN_POSITION){
                    setPosition(MAX_POSITION);
                }else if(x > MAX_POSITION){
                    setPosition(MAX_POSITION);
                }else {
                    setPosition((int) x);
                }
                if (mMove != null){
                    mMove.onMove(Double.parseDouble(String.format("%.1f",getFmChannel())));
                    Log.d("TAG", "position:" + position);
                    Log.d("TAG", "channel:" + getFmChannel());
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL:
                //只停在0.1(刻度线上)的位置
                setFmChanel(Double.parseDouble(String.format("%.1f", getFmChannel())));
                Log.d("停下来后", "channel:" + Double.parseDouble(String.format("%.1f", getFmChannel())));

                break;
            default:
                break;
        }


        return true;
    }

    public void setFmChanel(double v) {
        int temp = (int) ((v - 87) * 80) + 20;
        setPosition(temp);
    }

    public double getFmChannel() {
        return ((position - 20.0) / 80.0 + 87.0);
    }

    private void setPosition(int maxPosition) {
        position = maxPosition;
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(false);
        return super.dispatchTouchEvent(event);
    }



    /**
     * 定义监听接口
     */
    public interface OnMoveActionListener {
        void onMove(double x);
    }

    /**
     * 为每个接口设置监听器
     */
    public void setOnMoveActionListener(OnMoveActionListener move) {
        mMove = move;
    }
}
