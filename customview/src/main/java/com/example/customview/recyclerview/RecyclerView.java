package com.example.customview.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.example.customview.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerView extends ViewGroup {
    /**
     * 是否需要重绘，因为一般重绘是耗时的，所以需要一个变量控制，防止每次都进行重绘
     */
    private boolean needRelayout;
    /**
     * 当前显示的view
     */
    private List<View> views;
    private Adapter adapter;
    private int rowCount;
    /**
     * 每一行item的高度
     */
    private int[] heights;
    /**
     * recyclerview的高度和宽度
     */
    private int mWidth,mHeight;
    private Recycler recycler;
    /**
     * 移动的最小距离
     */
    private int touchSlop;
    private int currentY;
    /**
     * 滑动距离
     */
    private int scrollY;

    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        needRelayout = true;
        views = new ArrayList<>();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setAdapter(Adapter adapter){
        if(adapter != null){
            needRelayout = true;
            this.adapter = adapter;
            recycler = new Recycler(adapter.getViewTypeCount());
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     *
     * @param changed 这个参数表示父容器是否发生改变
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (needRelayout || changed){
            needRelayout = false;

            views.clear();
            removeAllViews();
            if (adapter != null){
                rowCount = adapter.getCount();
                heights = new int[rowCount];
                for ( int i = 0;i < rowCount; i++){
                    heights[i] = adapter.getHeight(i);
                }
                mHeight = b - t;
                mWidth = r-l;
                int top = 0;
                int bottom = 0;

                for ( int i = 0;i < rowCount && bottom < mHeight; i++){
                    bottom = top + heights[i];
                    /**
                     * recyclerview第一屏加载个数是怎么确定的
                     * 在onLayout中，每填充一个item，他的bottom都会加上它的高度，知道大于等于屏幕的高度，填充完毕
                     */
                    makeAndSetUp(i,0,top,mWidth,bottom);
                    top = bottom;
                }
            }
        }
    }

    private void makeAndSetUp(int i, int left, int top, int right, int bottom) {
        View view = obtain(i,right - left,bottom - top);
        view.layout(left,top,right,bottom);
    }

    private View obtain(int i, int width, int height) {
        /**
         * 回收池
         */
        View view;
        View recyclerView = recycler.getView(adapter.getItemViewType(i));
        if (recyclerView == null){
             view = adapter.onCreateViewHolder(i,null,this);
             if(view == null){
                 throw new RuntimeException("onCreateViewHolder必须初始化");
             }
        }else {
            view = adapter.onBinderViewHolder(i,recyclerView,this);
        }
        if(view == null){
            throw new RuntimeException("converView");
        }
        view.setTag(R.id.tag_row,i);
        view.measure(MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY));
        addView(view,0);
        return view;
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        int type = (int) view.getTag(R.id.tag_row);
        recycler.addView(type,view);
    }

    /**
     * 滑动冲突不能用距离来判断，因为不同的手机有不同的屏幕分辨率，滑动所需的距离不一样
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y2 = (int) Math.abs(event.getRawY() - currentY);
                if(y2 > touchSlop){
                    /**
                     * 如果外面有多个滑动控件包裹，我们可以遍历他的子view,通过判断是否在子view的范围里
                     */
                    intercept = true;
                }
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int y2= (int) event.getRawY();
                int diff = currentY - y2;
                scrollBy(0,diff);
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollY = y;

    }

    public interface Adapter{
        View onCreateViewHolder(int position,View  converView,ViewGroup parent);
        View onBinderViewHolder(int position,View converView,ViewGroup parent);
        int getItemViewType(int row);

        /**
         * 有多少种布局类型
         * @return
         */
        int getViewTypeCount();
        int getCount();
        int getHeight(int index);
    }
}
