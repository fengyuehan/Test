package com.example.customview.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.customview.R;

import java.util.ArrayList;

public class SwipeMenuLayout extends ViewGroup {

    private static final String TAG = "SwipeMenuLayout";
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>();
    /**
     * 左侧滑动的view的ID
     */
    private int mLeftViewId;
    /**
     * 右侧滑动的view的ID
     */
    private int mRightViewId;
    /**
     * 内容view的ID
     */
    private int mContentViewId;
    /**
     * 左侧的view
     */
    private View mLeftView;
    /**
     * 右侧的view
     */
    private View mRightView;
    /**
     * 中间的内容
     */
    private View mContentView;
    private MarginLayoutParams mMarginLayoutParams;
    /**
     * 判断是否侧滑
     */
    private boolean isSwipe;
    private PointF mLastP;
    private PointF mFirstP;
    private float mFraction = 0.3f;
    /**
     * 是否可以左滑
     */
    private boolean mCanLeftSwipe = true;
    /**
     * 是否可以右滑
     */
    private boolean mCanRightSwipe = true;
    private int mScaledTouchSlop;
    private Scroller mScroller;
    private static SwipeMenuLayout mViewCache;
    private static State mStateCache;
    private float finalyDistanceX;

    public SwipeMenuLayout(Context context) {
        this(context,null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        //系统 滑动距离的最小值，大于该值可以认为滑动
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        mScroller = new Scroller(context);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwipeMenuLayout,defStyleAttr,0);
        try {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++){
                int attr = typedArray.getIndex(i);
                if(attr == R.styleable.SwipeMenuLayout_leftMenuView){
                    mLeftViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_leftMenuView,-1);
                }else if(attr == R.styleable.SwipeMenuLayout_rightMenuView){
                    mRightViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_canRightSwipe,-1);
                }else if (attr == R.styleable.SwipeMenuLayout_contentView){
                    mContentViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_contentView,-1);
                }else if (attr == R.styleable.SwipeMenuLayout_canLeftSwipe){
                    mCanLeftSwipe = typedArray.getBoolean(R.styleable.SwipeMenuLayout_canLeftSwipe,true);
                }else if (attr == R.styleable.SwipeMenuLayout_canRightSwipe){
                    mCanRightSwipe = typedArray.getBoolean(R.styleable.SwipeMenuLayout_canRightSwipe,true);
                }else if(attr == R.styleable.SwipeMenuLayout_fraction){
                    mFraction = typedArray.getFloat(R.styleable.SwipeMenuLayout_fraction,0.5f);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setClickable(true);
        int count = getChildCount();
        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        /**
         * 遍历childViews
         */
        for (int i= 0 ; i < count; i++){
            View child = getChildAt(i);
            /**
             * 当子view是GONE时， 不需要测量
             */
            if(child.getVisibility() != GONE){
                measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                maxHeight = Math.max(maxHeight,child.getMeasuredHeight() + layoutParams.bottomMargin + layoutParams.topMargin);
                maxWidth = Math.max(maxWidth,child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                childState = combineMeasuredStates(childState,child.getMeasuredState());
                if(measureMatchParentChildren){
                    if (layoutParams.width == LayoutParams.MATCH_PARENT || layoutParams.height == LayoutParams.MATCH_PARENT){
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
