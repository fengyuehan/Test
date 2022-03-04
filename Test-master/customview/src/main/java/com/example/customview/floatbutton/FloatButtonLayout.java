package com.example.customview.floatbutton;

import android.content.Context;
import android.graphics.PointF;
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

/**
 * author : zhangzf
 * date   : 2021/1/13
 * desc   :
 */
public class FloatButtonLayout extends FrameLayout {
    /**
     * 可拽托按钮
     */
    private View mFloatButton;
    /**
     * 拽托帮助类
     */
    private ViewDragHelper mViewDragHelper;

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    /**
     * 回调
     */
    private Callback mCallback;

    public FloatButtonLayout(@NonNull Context context) {
        this(context,null);
    }

    public FloatButtonLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatButtonLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /**
         * 参数sensitivity，这个sensitivity是用来设置mTouchSlop的，它值越大mTouchSlop就会越小，就会越敏感，也就是滑动的时候判断move的间距越短。
         */
        mViewDragHelper = ViewDragHelper.create(this, 0.3f, new ViewDragHelper.Callback() {

            /**
             * 开始拽托时的X坐标
             */
            private int mDownX;
            /**
             * 开始拽托时的Y坐标
             */
            private int mDownY;
            /**
             * 开始拽托时的时间
             */
            private long mDownTime;
            //尝试捕获被拖拽的view，如果返回true代表可以被拖拽，返回false代表不可以被拖拽
            //var1:被拖拽的view
            //使用时判断需要被拖拽的view是否等等于var1。
            //一般判断很多view其中哪些是否可以移动时使用
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mFloatButton;
            }

            //View的拖拽状态改变时触发
            //STATE_IDLE:　未被拖拽
            //STATE_DRAGGING：正在被拖拽
            //STATE_SETTLING:　被安放到一个位置中的状态
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            //拖拽时的（开始移动）触发
            //changeView:当前被拖拽的view
            //left:拖动时left坐标
            //top:拖动时top坐标
            //dx:拖拽时x轴偏移量
            //dy:拖拽时y轴偏移量
            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            //view被捕获时触发（也就是按下）
            //capturedChild：捕获的view
            //activePointerId：按下手指的id,多指触控时会用到
            //一般用于做准备初始化工作
            @Override
            public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                mDownX = capturedChild.getLeft();
                mDownY = capturedChild.getTop();
                mDownTime = System.currentTimeMillis();
            }

            //view被放下时触发
            //releasedChild被放下的view
            //xvel：释放View的x轴方向上的加速度
            //yvel：释放View的y轴方向上的加速度
            //一般用于收尾工作
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                //松手回弹，判断如果松手位置，近左边还是右边，进行弹性滑动
                int fullWidth = getMeasuredWidth();
                final int halfWidth = fullWidth / 2;
                final int currentLeft = releasedChild.getLeft();
                final int currentTop = releasedChild.getTop();
                //滚动到左边
                final Runnable scrollToLeft = new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        mViewDragHelper.settleCapturedViewAt(getPaddingStart(), currentTop);
                    }
                };
                //滚动到右边
                final Runnable scrollToRight = new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        int endX = getMeasuredWidth() - getPaddingEnd() - releasedChild.getWidth();
                        mViewDragHelper.settleCapturedViewAt(endX, currentTop);
                    }
                };
                Runnable checkDirection = new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void run() {
                        if (currentLeft < halfWidth) {
                            //在屏幕一半的左边，回弹回左边
                            scrollToLeft.run();
                        } else {
                            //在屏幕一半的右边，回弹回右边
                            scrollToRight.run();
                        }
                    }
                };
                //最小移动距离
                int minMoveDistance = fullWidth / 3;
                //计算移动距离
                int distanceX = currentLeft - mDownX;
                int distanceY = currentTop - mDownY;
                long upTime = System.currentTimeMillis();
                //间隔时间
                long intervalTime = upTime - mDownTime;
                float touched = getDistanceBetween2Points(new PointF(mDownX, mDownY), new PointF(currentLeft, currentTop));
                //处理点击事件，移动距离小于识别为移动的距离，并且时间小于400
                if (touched < mViewDragHelper.getTouchSlop() && intervalTime < 300) {
                    if (mCallback != null) {
                        mCallback.onClickFloatButton();
                    }
                    //因为判断为点击事件后，return就会让按钮不进行贴边回弹了，这里再添加处理，让可以贴边回弹
                    checkDirection.run();
                    return;
                }
                //判断上下滑还是左右滑
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    //左右滑，滑动得少，并且速度很快，则为fling操作
                    if (Math.abs(distanceX) < minMoveDistance &&
                            Math.abs(xvel) > Math.abs(mViewDragHelper.getMinVelocity())) {
                        //距离相减为正数，则为往右滑
                        if (distanceX > 0) {
                            scrollToRight.run();
                        } else {
                            //否则为往左
                            scrollToLeft.run();
                        }
                    } else {
                        //不是fling操作，判断松手位置在屏幕左边还是右边
                        checkDirection.run();
                    }
                } else {
                    //上下滑，主要是判断在屏幕左还是屏幕右，不需要判断fling
                    checkDirection.run();
                }
                invalidate();
            }

            //返回view在水平方向的位置，
            //left:当前被拖拽的的view要移动到的的left值
            //dx:移动的偏移量
            //返回0则无法移动，通常直接返回left
            //一般必须重写此方法返回left
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                int leftBound = getPaddingStart();
                int rightBound = getMeasuredWidth() - getPaddingEnd() - child.getWidth();
                if (left < leftBound){
                    return leftBound;
                }
                if (left > rightBound){
                    return rightBound;
                }
                return left;
            }

            //返回view在竖直方向的位置，
            //top:当前被拖拽的的view要移动到的的left值
            //dy:移动的偏移量
            //返回0则无法移动，通常直接返回top
            //一般必须重写此方法返回top
            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                //限制上下移动的返回，不能超过父控件
                int topBound = getPaddingTop();
                int bottomBound = getMeasuredHeight() - getPaddingBottom() - child.getHeight();
                if (top < topBound) {
                    return topBound;
                }
                if (top > bottomBound) {
                    return bottomBound;
                }
                return top;
            }

            /**
             * 水平方向活动范围
             * @param child
             * @return
             */
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {
                return getMeasuredWidth() - getPaddingStart() - getPaddingEnd() - child.getWidth();
            }

            /**
             * 垂直方向活动范围
             * @param child
             * @return
             */
            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - child.getHeight();
            }
        });
    }

    //XML布局被加载完后，就会回调onFinshInfalte这个方法，在这个方法中我们可以初始化控件和数据。
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFloatButton = findViewById(R.id.float_button);
        if (mFloatButton == null) {
            throw new NullPointerException("必须要有一个可拽托按钮");
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)){
            invalidate();
        }
    }

    /**
     * 获得两点之间的距离
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        return (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
    }
}
