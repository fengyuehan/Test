package com.example.customview.downMenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.customview.R;


/**
 * author : zhangzf
 * date   : 2021/5/25
 * desc   :
 */
public class DropDownMenuLayout extends LinearLayout {
    private Context mContext;
    //阴影遮罩
    private View mShadeView;
    //包裹阴影遮罩和弹出的内容以及tab的ViewGroup
    private FrameLayout mContentLayout;
    //弹出的内容
    private FrameLayout mMenuContentView;
    //tab
    private LinearLayout mMenuTabLayout;
    //弹出的内容的高度
    private int mMenuContentHeight;
    //tab当前选择的位置
    private int mCurrentTabPosition = -1;
    //动画的时间
    private int mAnimatorDuration = 500;
    //动画是否停止  出现一种情况，当动画没有停止时，快速点击的时候，会崩溃
    private boolean mAnimatorExecute;
    //弹出的内容的最大高度百分比
    private float mMaxMenuHeighPercent = 0.75f;
    //弹出的内容的高度百分比
    private float mMenuHeighPercent;

    private BaseMenuAdapter mAdapter;

    public DropDownMenuLayout(Context context) {
        this(context,null);
    }

    public DropDownMenuLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DropDownMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.DropDownMenuLayout);
        mMenuHeighPercent = array.getFloat(R.styleable.DropDownMenuLayout_menuMaxMenuPercent,mMaxMenuHeighPercent);
        array.recycle();
        init();
    }

    /**
     * 构建view
     */
    private void init() {
        //初始化tab
        mMenuTabLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        mMenuTabLayout.setLayoutParams(layoutParams);
        mMenuTabLayout.setOrientation(HORIZONTAL);

        //初始化ContentLayout
        mContentLayout = new FrameLayout(mContext);
        mContentLayout.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        //初始化弹出的内容
        //如果没有重新设置LayoutParams，则表示用上面的LayoutParams，所以mMenuContentView的LayoutParams
        // 为FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        mMenuContentView = new FrameLayout(mContext);
        mMenuContentView.setBackgroundColor(Color.WHITE);
        //阴影
        mShadeView = new View(mContext);
        mShadeView.setBackgroundColor(Color.parseColor("#66CCCCCC"));
        mShadeView.setAlpha(0.0f);
        setShadowClick(mShadeView);

        mContentLayout.addView(mShadeView);
        mContentLayout.addView(mMenuContentView);

        //必须添加这句，不然导致tab不能显示
        setOrientation(VERTICAL);
        addView(mMenuTabLayout);

        //设置tab下面的下划线
        View dividerView = new View(mContext);
        dividerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dp2px
                (mContext, 1)));
        dividerView.setBackgroundColor(Color.GRAY);
        addView(dividerView);
        addView(mContentLayout);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //这个判断是弹出的内容打开的时候，再点击tab切换弹出的内容，会导致重新测量，然后本来打开的却关闭了，加上这个判断，只有当关闭的时候才设置这个值
        if (mMenuContentHeight == 0){
            int height = MeasureSpec.getSize(heightMeasureSpec);
            //设置弹出的内容的最大高度
            if (mMenuHeighPercent < mMaxMenuHeighPercent){
                mMenuContentHeight = (int) (height * mMenuHeighPercent);
            }else {
                mMenuContentHeight = (int) (height * mMaxMenuHeighPercent);
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mMenuContentView.getLayoutParams();
            params.height = mMenuContentHeight;
            mMenuContentView.setLayoutParams(params);
            //一开始进来是关闭的
            mMenuContentView.setTranslationY(-mMenuContentHeight);
        }
    }

    public void setCustomAdapter(BaseMenuAdapter adapter){
        if (adapter == null){
            throw new NullPointerException("adapter不能为空");
        }
        this.mAdapter = adapter;
        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++){
            View tabView = mAdapter.getTabView(i,mMenuTabLayout);
            LinearLayout.LayoutParams params = (LayoutParams) tabView.getLayoutParams();
            params.weight = 1;
            tabView.setLayoutParams(params);

            //添加分割线
            View view = new View(getContext());
            view.setLayoutParams(new LayoutParams(DisplayUtils.dp2px(mContext,1), ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(Color.parseColor("0xffcccccc"));
            //最后一个不需要添加分割线
            if (i < count -1){
                mMenuTabLayout.addView(view);
            }

            setTabClick(i,tabView);

            //弹出的内容
            View menuView = mAdapter.getMenuView(i,mMenuContentView);
            menuView.setVisibility(GONE);
            mMenuContentView.addView(menuView);
        }
    }

    /**
     * tab的点击事件
     * @param position
     * @param tabView
     */
    private void setTabClick(int position, View tabView) {
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentTabPosition == -1){
                    //表示当前是关闭的，需要打开
                    openMenu(position,tabView);
                }else {
                    //表示当前是打开的，需要关闭
                    if (mCurrentTabPosition == position){
                        //表示点击的是同一个tab
                        closeMenu();
                    }else {
                        //点击的不是同一个位置，直接把前一个设置为Gone,后一个为Visibale
                        View menuView = mMenuContentView.getChildAt(mCurrentTabPosition);
                        menuView.setVisibility(GONE);
                        mAdapter.menuClose(mMenuContentView.getChildAt(mCurrentTabPosition));
                        mCurrentTabPosition = position;
                        menuView = mMenuContentView.getChildAt(mCurrentTabPosition);
                        menuView.setVisibility(VISIBLE);
                        mAdapter.menuOpen(mMenuContentView.getChildAt(mCurrentTabPosition));
                    }
                }
            }
        });
    }

    /**
     * 打开菜单的动画
     * @param position
     * @param tabView
     */
    private void openMenu(int position, View tabView) {
        if (mAnimatorExecute) {
            return;
        }
        mShadeView.setVisibility(VISIBLE);
        View menuView = mMenuContentView.getChildAt(position);
        menuView.setVisibility(VISIBLE);

        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContentView,
                "translationY", -mMenuContentHeight, 0f);
        translationAnimator.setDuration(mAnimatorDuration);
        translationAnimator.start();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadeView,
                "alpha", 0f, 1f);
        alphaAnimator.setDuration(mAnimatorDuration);

        alphaAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentTabPosition = position;
                mAnimatorExecute = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimatorExecute = true;
                mAdapter.menuOpen(mMenuTabLayout.getChildAt(position));
            }
        });
        alphaAnimator.start();
    }

    /**
     * 阴影部分的点击事件
     * @param mShadeView
     */
    private void setShadowClick(View mShadeView) {
        mShadeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
    }

    /**
     * 关闭菜单的动画
     */
    private void closeMenu() {
//如果动画正在执行。则直接返回
        if (mAnimatorExecute){
            return;
        }
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mMenuContentView,"translationY",0,-mMenuContentHeight);
        translationAnimator.setDuration(mAnimatorDuration);
        translationAnimator.start();

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mShadeView,"alpha",1f,0f);
        alphaAnimator.setDuration(mAnimatorDuration);
        alphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorExecute = false;
                View menuView = mMenuContentView.getChildAt(mCurrentTabPosition);
                menuView.setVisibility(GONE);
                mShadeView.setVisibility(GONE);
                mCurrentTabPosition = -1;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mAnimatorExecute = true;
                mAdapter.menuClose(mMenuTabLayout.getChildAt(mCurrentTabPosition));
            }
        });
        //这个必须写在addListener的后面，不然addListener里面的东西不会走。（遇到的问题）
        alphaAnimator.start();
    }
}
