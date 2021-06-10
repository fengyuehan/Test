package com.example.customview.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ListViewCompat;
import androidx.customview.widget.ViewDragHelper;



/**
 * author : zhangzf
 * date   : 2021/5/22
 * desc   :
 */
public class VerticalDragView extends FrameLayout {
    private ViewDragHelper mViewDragHelper;
    private View mDragListView;
    private int mMenuHeight;
    private boolean mMenuIsOpen;

    public VerticalDragView(@NonNull Context context) {
        this(context,null);
    }

    public VerticalDragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerticalDragView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            /**
             * 决定是否能拖动
             * @param child
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mDragListView;
            }

            //必须写这个，不然拖不动，标识往哪个方向拖动
            @Override
            public int clampViewPositionVertical(@NonNull  View child, int top, int dy) {
                if (top < 0){
                    top = 0;
                }
                if (top > mMenuHeight){
                    top = mMenuHeight;
                }
                return top;
            }

            //放手的时候回调
            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (releasedChild == mDragListView){
                    if (mDragListView.getTop() > mMenuHeight /2){
                        mViewDragHelper.settleCapturedViewAt(0,mMenuHeight);
                        mMenuIsOpen = true;
                    }else {
                        mViewDragHelper.settleCapturedViewAt(0,0);
                        mMenuIsOpen = false;
                    }
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mMenuHeight = getChildAt(0).getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private float mDownY;

    /**
     * ACTION_DOWN没有收到
     *
     * 拦截：如果返回true，则相当于不分发给下面，则走自己的流程，会走自己的onTouch()
     *      如果返回super，则表示继续分发给下面，就会走子的onTouch，然后不会走自己的
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //菜单打开需要全部拦截
        if (mMenuIsOpen){
            return true;
        }
        //向下滑动的时候，父类拦截，不需要给recyclerview
        //向上滑动的时候，交给recyclerview处理
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mViewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if ((moveY - mDownY) > 0 && canChildScrollUp()){
                    //向下滑动，并且滚动到顶部，拦截不让recyclerview
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2){
            throw new RuntimeException("只能有两个View");
        }
        mDragListView = getChildAt(1);
    }

    public boolean canChildScrollUp() {

        if (mDragListView instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mDragListView, -1);
        }
        return mDragListView.canScrollVertically(-1);
    }
}
