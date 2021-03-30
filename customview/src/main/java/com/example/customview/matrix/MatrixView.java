package com.example.customview.matrix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author : zhangzf
 * date   : 2021/3/23
 * desc   :
 */
public class MatrixView extends View {
    private Rect rect;
    private Paint paint;
    private Matrix matrix;

    public MatrixView(Context context) {
        this(context,null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        rect = new Rect(0,0,200,200);
        paint = new Paint();
        paint.setAntiAlias(true);
        matrix = new Matrix();
    }


    /**
     * preTranslate()->称为“左乘”，将平移矩阵称为T，“左乘”的意思是将当前存在的矩阵I“乘”待变换的平移矩阵T，新矩阵的结果为：M=I * T，pre是在队列最前面插入，post是在队列最后面追加，而set先清空队列在添加。也就是后插入的pre会放在最前面。
     * postTranslate方法与preTranslate方法相反，称为“右乘”，“右乘”的意思是将待变换的平移矩阵T“乘”当前存在的矩阵I，新矩阵的结果为：M=T * I
     * setTranslate则会把前面的全部清空
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rect,paint);
        matrix.preTranslate(0,300);
        matrix.postTranslate(0,300);
        matrix.setTranslate(0,200);
        //matrix.setTranslate(300, 0);//1
        matrix.preTranslate(100, 0);//2
        matrix.postTranslate(50, 0);//3
        canvas.setMatrix(matrix);
        paint.setColor(Color.RED);
        canvas.drawRect(rect,paint);
    }
}
