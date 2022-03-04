package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @ClassName BaseFragment
 * @Description TODO
 * @Author user
 * @Date 2019/12/2
 * @Version 1.0
 */
public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;
    public View mRoot;
    //protected Gsoxn mGson;
    //private JsonParser mJsonParser;
    // 两次点击间隔不能少于1000ms
    private static final int MIN_DELAY_TIME = 1000;
    private long mLastClickTime;

    //Fragment的View加载完毕的标记
    protected boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    //是否已经懒加载
    private boolean mIsLazyLoaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (BaseActivity) getActivity();
        //Logger.t(Constants.USER_TAG).e("Fragment onCreate:" + getClass().getSimpleName());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //mGson = new Gson();
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = initView();
        }
        //Logger.t(Constants.USER_TAG).e("Fragment onCreateView:" + getClass().getSimpleName());
        return mRoot;
    }

    protected abstract View initView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initListener();
        //onViewCreated，isViewCreated设为可见
        isViewCreated = true;
        //此时是没有加载数据的，所以胃false
         mIsLazyLoaded = false;
        lazyLoad();
        //Logger.t(Constants.USER_TAG).e("Fragment onViewCreated:" + getClass().getSimpleName());
    }

    protected abstract void initListener();

    protected abstract void initData();


    protected void onLazyLoadData() {
    }

    protected void onRefreshData() {
    }

    protected boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime > MIN_DELAY_TIME) {
            mLastClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            //Logger.t(Constants.USER_TAG).e("Fragment setUserVisibleHint:" + getClass().getSimpleName());
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            //Logger.t(Constants.USER_TAG).e("Fragment onLazyLoadData:" + getClass().getSimpleName());
            onLazyLoadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
            mIsLazyLoaded = true;
        } else if (mIsLazyLoaded && isUIVisible) {
            //Logger.t(Constants.USER_TAG).e("Fragment onRefreshData:" + getClass().getSimpleName());
            //非首次该fragment显示，适合第二次后每次进入页面，刷新数据使用
            onRefreshData();
        }
    }
}
