package com.example.keyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


@SuppressLint("AppCompatCustomView")
public class PasswordEditText extends EditText {
    private Paint paint,paint2,paint3;
    // 一个密码所占的宽度
    private int mPasswordItemWidth;
    // 密码的个数默认为6位数
    private int mPasswordNumber = 6;
    // 背景边框颜色
    private int mBgColor = Color.parseColor("#d1d2d6");
    // 背景边框大小
    private int mBgSize = 1;
    // 背景边框圆角大小
    private int mBgCorner = 0;
    // 分割线的颜色
    private int mDivisionLineColor = mBgColor;
    // 分割线的大小
    private int mDivisionLineSize = 1;
    // 密码圆点的颜色
    private int mPasswordColor = mDivisionLineColor;
    // 密码圆点的半径大小
    private int mPasswordRadius = 4;

    private PasswordFullListener mListener;

    public PasswordEditText(Context context) {
        super(context);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAttributeSet(context, attrs);
        // 设置输入模式是密码
        setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);

    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置防抖动
        paint.setDither(true);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setDither(true);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setDither(true);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.PassWordEditText);
        mDivisionLineSize = (int) array.getDimension(R.styleable.PassWordEditText_divisionLineSize, dip2px(mDivisionLineSize));
        mPasswordRadius = (int) array.getDimension(R.styleable.PassWordEditText_passwordRadius,dip2px(mPasswordRadius));
        mBgSize = (int) array.getDimension(R.styleable.PassWordEditText_bgSize,dip2px(mBgSize));
        mBgCorner = (int) array.getDimension(R.styleable.PassWordEditText_bgCorner,dip2px(mBgCorner));

        mBgColor = array.getColor(R.styleable.PassWordEditText_bgColor,mBgColor);
        mDivisionLineColor = array.getColor(R.styleable.PassWordEditText_divisionLineColor,mDivisionLineColor);
        mPasswordColor = array.getColor(R.styleable.PassWordEditText_passwodColor,mPasswordColor);

        mPasswordNumber = array.getInteger(R.styleable.PassWordEditText_passwordNumber,mPasswordNumber);
        array.recycle();

    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //不将这行代码注释掉，会在背后有数字显示
        //super.onDraw(canvas);
        int passwordWidth = getWidth() - (mPasswordNumber -1) * mDivisionLineSize;
        mPasswordItemWidth = passwordWidth / mPasswordNumber;

        drawBg(canvas);
        // 绘制分割线
        drawDivisionLine(canvas);
        // 绘制密码
        drawHidePassword(canvas);
    }

    private void drawHidePassword(Canvas canvas) {
        int passwordWidth = getText().length();
        paint3.setColor(mPasswordColor);
        paint3.setStyle(Paint.Style.FILL);
        for (int i = 0; i < passwordWidth; i++){
            int cx = i * mDivisionLineSize + i * mPasswordItemWidth + mPasswordItemWidth / 2 + mBgSize;
            canvas.drawCircle(cx,getHeight()/2,mPasswordRadius,paint3);
        }

       if (passwordWidth > mPasswordNumber){
            if (mListener != null){
                mListener.passwordFull(getText().toString().trim());
            }
       }
    }

    private void drawDivisionLine(Canvas canvas) {
        paint2.setStrokeWidth(mDivisionLineSize);
        paint2.setColor(mDivisionLineColor);
        for (int i = 0;i <mPasswordNumber -1;i++){
            int startX = (i + 1) * mDivisionLineSize + (i + 1) * mPasswordItemWidth + mBgSize;
            canvas.drawLine(startX,mBgSize,startX,getHeight()-mBgSize,paint2);
        }
    }

    public void addPassword(String number){
        number = getText().toString().trim() + number;
        if (number.length() > mPasswordNumber){
            return;
        }
        setText(number);
    }

    /**
     * 删除最后一位密码
     */
    public void deleteLastPassword() {
        String currentText = getText().toString().trim();
        if (TextUtils.isEmpty(currentText)) {
            return;
        }
        currentText = currentText.substring(0, currentText.length() - 1);
        setText(currentText);
    }

    private void drawBg(Canvas canvas) {
        paint.setColor(mBgColor);
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(mBgSize,mBgSize,getWidth() - mBgSize,getHeight() - mBgSize);
        if (mBgCorner == 0){
            canvas.drawRect(rectF,paint);
        }else {
            canvas.drawRoundRect(rectF,mBgCorner,mBgCorner,paint);
        }
    }


    /**
     * 设置密码填充满的监听
     */
    public void setOnPasswordFullListener(PasswordFullListener listener) {
        this.mListener = listener;
    }

    /**
     * 密码已经全部填满
     */
    public interface PasswordFullListener {
        public void passwordFull(String password);
    }
}
