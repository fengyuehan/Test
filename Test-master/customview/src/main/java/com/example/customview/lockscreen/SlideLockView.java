package com.example.customview.lockscreen;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.customview.widget.ViewDragHelper;

import com.example.customview.R;

public class SlideLockView extends FrameLayout {
    /**
     * 滑动滑块
     */
    private View mLockBtn;
    /**
     * 拽托帮助类
     */
    private ViewDragHelper mViewDragHelper;
    /**
     * 回调
     */
    private Callback mCallback;
    /**
     * 是否解锁
     */
    private boolean isUnlock = false;
    /**
     * 滑块宽度
     */
    int lockBtnWidth;
    /**
     * 轨道宽度
     */
    int fullWidth;

    int leftMaxDinstance;
    public SlideLockView(@NonNull Context context) {
        this(context,null);
    }

    public SlideLockView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideLockView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final SlideLockView slideRail = this;
        mViewDragHelper = ViewDragHelper.create(this, 0.3f, new ViewDragHelper.Callback() {
            private int mTop;

            /**
             * 尝试捕获被拖拽的view，如果返回true代表可以被拖拽，返回false代表不可以被拖拽
             * 使用时判断需要被拖拽的view是否等等于var1
             * 一般判断很多view其中哪些是否可以移动时使用
             * @param child
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mLockBtn;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {

                int leftMinDistance = getPaddingStart();

                if (left < leftMinDistance){
                    left = leftMinDistance;
                }else if (left > leftMaxDinstance){
                    return leftMaxDinstance;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return getPaddingTop();
            }

            /**
             * view按下时触发，一般用于初始化工作
             * @param capturedChild
             * @param activePointerId
             */
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                mTop = capturedChild.getTop();
                lockBtnWidth = mLockBtn.getWidth();
                leftMaxDinstance = fullWidth - getPaddingEnd() - lockBtnWidth;
                fullWidth = slideRail.getWidth();
            }

            /**
             * 边缘触摸时触发（需开启边缘触摸）
             * @param edgeFlags 触摸的位置EDGE_LEFT,EDGE_TOP,EDGE_RIGHT,EDGE_BOTTOM
             * @param pointerId 按下手指的id,多指触控时会用到
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            /**
             * view被放下时触发，一般用于首尾工作
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                /**
                 * 获取当前的位置
                 */
                int currentLeft = releasedChild.getLeft();
                int halfWidth = fullWidth /2;
                if (currentLeft <= halfWidth && xvel < 1000){
                    mViewDragHelper.settleCapturedViewAt(getPaddingStart(),mTop);
                }else {
                    mViewDragHelper.settleCapturedViewAt(fullWidth - getPaddingEnd()-lockBtnWidth,mTop);
                }
                invalidate();
            }

            /**
             * view拖拽状态改变
             * STATE_IDLE:　未被拖拽
             * STATE_DRAGGING：正在被拖拽
             * STATE_SETTLING:　被安放到一个位置中的状态
             * @param state
             */
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
                int left = mLockBtn.getLeft();
                if (state == ViewDragHelper.STATE_IDLE){
                    if (left == leftMaxDinstance){
                        if (!isUnlock){
                            isUnlock = true;
                            if (mCallback != null){
                                mCallback.onUnlock();
                            }
                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLockBtn = findViewById(R.id.lock_btn);
        if (mLockBtn == null) {
            throw new NullPointerException("必须要有一个滑动滑块");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper != null){
            if (mViewDragHelper.continueSettling(true)){
                invalidate();
            }
        }
    }

    public interface Callback {
        /**
         * 当解锁时回调
         */
        void onUnlock();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }
}
