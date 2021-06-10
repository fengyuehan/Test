package com.example.nestedsrcolldemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * author : zhangzf
 * date   : 2021/5/17
 * desc   :
 */
public class NestedTraditionLayout extends LinearLayout {
    private int mLastY;
    private boolean isHeadShow;
    private View mHeadView;
    private View mNavView;
    private ViewPager mViewPager;

    private int mHeadTopHeight;

    public NestedTraditionLayout(Context context) {
        this(context,null);
    }

    public NestedTraditionLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NestedTraditionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) (mLastY - ev.getY());
                if (Math.abs(dy) > ViewConfiguration.getTouchSlop()){
                    //向上滑动，且头部没有隐藏，父类拦截
                    if (dy > 0 && !isHeadShow){
                        return true;
                    }
                    //向下滑动，且头部是隐藏的的，父类拦截
                    if (dy < 0 && isHeadShow){
                        return true;
                    }
                }
                break;
        }
        //表示不拦截，交给子类
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastY - y;
                if (Math.abs(dy) > ViewConfiguration.getTouchSlop()) {
                    scrollBy(0, dy);
                }
                mLastY = y;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0){
            y = 0;
        }
        if (y > mHeadTopHeight ){
            y = mHeadTopHeight;
        }
        super.scrollTo(x, y);
        isHeadShow = getScrollY() == mHeadTopHeight;

    }

    /**
     * 解决上划ViewPager出现空白的bug
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams = mViewPager.getLayoutParams();
        layoutParams.height  = getMeasuredHeight() - mNavView.getMeasuredHeight();
        mViewPager.setLayoutParams(layoutParams);
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * getMeasuredHeight 与getHeight的区别
     * getMeasureHeight就是自身的测量高度，不考虑父布局高度，我测量自己是多高拿到的getMeasureHeight就是多少，setMeasuredDimension之后就生效
     * getHeight是父布局告诉我多高，我拿到的就是多少。onLayout后生效
     * 两种方法获取到的都不是View的真实高度。
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeadTopHeight = mHeadView.getMeasuredHeight();
    }

    /**
     * xml解析完成的回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeadView = findViewById(R.id.iv_head_image);
        mNavView = findViewById(R.id.tab);
        mViewPager = findViewById(R.id.vp);
        if (!(mViewPager instanceof ViewPager)) {
            throw new RuntimeException("id view_pager should be viewpager!");
        }
    }
}
