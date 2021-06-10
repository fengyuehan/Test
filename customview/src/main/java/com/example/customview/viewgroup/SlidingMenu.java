package com.example.customview.viewgroup;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.customview.R;

/**
 * author : zhangzf
 * date   : 2021/5/22
 * desc   :
 */
public class SlidingMenu extends HorizontalScrollView {
    private Context mContext;
    //侧面的布局
    private View mMenuView;
    //主布局
    private View mContentView;
    //手势
    private GestureDetector mGestureDetector;
    //侧面布局是否打开
    private boolean mIsMenuOpen;
    //阴影
    private View mShadowView;
    //侧边的宽度
    private int mMenuWidth;
    //是否拦截
    private boolean isIntercept;


    public SlidingMenu(Context context) {
        this(context,null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        float rightPadding = typedArray.getDimension(R.styleable.SlidingMenu_rightPadding,dip2px(50));
        //侧边的宽度为屏幕宽度减去右边padding
        mMenuWidth = (int) (getScreenWidth() - rightPadding);
        typedArray.recycle();
        mGestureDetector = new GestureDetector(context,new GestureListener());

    }

    /**
     * 这个方法是在解析完xml文件后调用的回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup container = (ViewGroup) getChildAt(0);
        int childCound = getChildCount();
        if (childCound != 2){
            throw new RuntimeException("有且只有两个子布局");
        }
        mMenuView = container.getChildAt(0);
        ViewGroup.LayoutParams layoutParams = mMenuView.getLayoutParams();
        layoutParams.width = mMenuWidth;
        mMenuView.setLayoutParams(layoutParams);

        mContentView = container.getChildAt(1);

        ViewGroup.LayoutParams params = mContentView.getLayoutParams();
        container.removeView(mContentView);
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.addView(mContentView);
        //添加阴影
        mShadowView = new View(getContext());
        mShadowView.setBackgroundColor(Color.parseColor("#55000000"));
        mShadowView.setAlpha(0.0f);
        relativeLayout.addView(mShadowView);

        params.width = getScreenWidth();
        container.addView(relativeLayout);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //一开始保证是打开的
        scrollTo(0,0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isIntercept = false;
        if (mIsMenuOpen){
            float currentX = ev.getX();
            //表示点击右边的阴影部分，则直接关闭
            if (currentX > mMenuWidth){
                closeMenu();
                isIntercept = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 关闭
     */
    private void closeMenu() {
        mIsMenuOpen = false;
        smoothScrollTo(0,0);
    }

    /**
     * 打开
     */
    private void openMenu(){
        //带动画 打开
        mIsMenuOpen = true;
        smoothScrollTo(mMenuWidth,0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isIntercept){
            return true;
        }
        if (mGestureDetector.onTouchEvent(ev)){
            float currentScrollX = getScrollX();
            if(currentScrollX > mMenuWidth / 2){
                //关闭
                closeMenu();
            }else {
                //打开
                openMenu();
            }
            //这里返回是为了保证了 super不执行 否则 smoothScrollTo() 没有效果
            return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //算一个比例
        float scale = (float) l/mMenuWidth;

        //设置慢慢变半透明
        mShadowView.setAlpha(1-scale);

        mContentView.setTranslationX(scale);

        mMenuView.setAlpha(1-scale);

        // 退出 按钮一开始 是在右边 被覆盖的
        // 通过平移实现
        mMenuView.setTranslationX(0.6f * l);
    }

    //屏幕的宽度
    private int getScreenWidth() {
        Resources resources = this.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    private float dip2px(int i) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,i,getResources().getDisplayMetrics());
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}
