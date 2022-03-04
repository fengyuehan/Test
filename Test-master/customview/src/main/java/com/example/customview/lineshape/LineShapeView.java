package com.example.customview.lineshape;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineShapeView extends View {
    /**
     * 线条形状有四种表现方式
     * setStrokeWidth(float width)
     * setStrokeCap(Paint.Cap cap)
     * setStrokeJoin(Paint.Join join)
     * setStrokeMiter(float miter)
     */

    private Paint mStrokeWidthPaint1,mStrokeWidthPaint2,mStrokeWidthPaint3;
    private Paint mStrokeCapPaint1,mStrokeCapPaint2,mStrokeCapPaint3;
    private Paint mStrokeJoinPaint1,mStrokeJoinPaint2,mStrokeJoinPaint3;
    private Paint mStrokeMiterPaint1,mStrokeMiterPaint2,mStrokeMiterPaint3;
    public LineShapeView(Context context) {
        super(context);
        init();
    }

    public LineShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mStrokeWidthPaint1 = new Paint();
        mStrokeWidthPaint1.setStrokeWidth(1);
        mStrokeWidthPaint2 = new Paint();
        mStrokeWidthPaint1.setStrokeWidth(5);
        mStrokeWidthPaint3 = new Paint();
        mStrokeWidthPaint1.setStrokeWidth(40);
        /**
         * 线头形状有三种：BUTT 平头、ROUND 圆头、SQUARE 方头。默认为 BUTT。
         */
        mStrokeCapPaint1 = new Paint();
        mStrokeCapPaint2 = new Paint();
        mStrokeCapPaint3 = new Paint();
        /**
         * 设置拐角的形状。有三个值可以选择：MITER 尖角、 BEVEL 平角和 ROUND 圆角。默认为 MITER
         */
        mStrokeJoinPaint1 = new Paint();
        mStrokeJoinPaint2 = new Paint();
        mStrokeJoinPaint3 = new Paint();
        mStrokeMiterPaint1 = new Paint();
        mStrokeMiterPaint2 = new Paint();
        mStrokeMiterPaint3 = new Paint();

    }
}
