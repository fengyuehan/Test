package com.example.otcchatkeyboard;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FuncLayout extends LinearLayout {
    public final int DEF_KEY = Integer.MIN_VALUE;

    private final SparseArray<View> mFuncViewArrayMap = new SparseArray<>();

    private int mCurrentFuncKey = DEF_KEY;

    protected int mHeight = 0;

    public FuncLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public void addFuncView(int key, View view) {
        if (mFuncViewArrayMap.get(key) != null) {
            return;
        }
        mFuncViewArrayMap.put(key, view);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
        view.setVisibility(GONE);
    }

    public void hideAllFuncView() {
        for (int i = 0; i < mFuncViewArrayMap.size(); i++) {
            int keyTemp = mFuncViewArrayMap.keyAt(i);
            mFuncViewArrayMap.get(keyTemp).setVisibility(GONE);
        }
        mCurrentFuncKey = DEF_KEY;
        setVisibility(false);
    }

    public void setVisibility(boolean b) {
        LayoutParams params = (LayoutParams) getLayoutParams();
        if (b) {
            setVisibility(VISIBLE);
            params.height = mHeight;
            if (mListenerList != null) {
                for (OnFuncKeyBoardListener mOnFuncKeyBoardListener : mListenerList) {
                    mOnFuncKeyBoardListener.OnFuncPop(mHeight);
                }
            }
        } else {
            setVisibility(GONE);
            params.height = 0;
            if (mListenerList != null) {
                for (OnFuncKeyBoardListener mOnFuncKeyBoardListener : mListenerList) {
                    mOnFuncKeyBoardListener.OnFuncClose();
                }
            }
        }
        setLayoutParams(params);
    }


    public void toggleFuncView(int key, boolean isSoftKeyboardPop, EditText editText) {
        if (getCurrentFuncKey() == key) {
            if (isSoftKeyboardPop) {
                if(KeyboardUtils.isFullScreen((Activity) getContext())){
                    KeyboardUtils.closeSoftKeyboard(editText);
                } else {
                    KeyboardUtils.closeSoftKeyboard(getContext());
                }
            } else {
                KeyboardUtils.openSoftKeyboard(editText);
            }
        } else {
            if (isSoftKeyboardPop) {
                if(KeyboardUtils.isFullScreen((Activity) getContext())){
                    KeyboardUtils.closeSoftKeyboard(editText);
                } else {
                    KeyboardUtils.closeSoftKeyboard(getContext());
                }
            }
            showFuncView(key);
        }
    }

    public void showFuncView(int key) {
        if (mFuncViewArrayMap.get(key) == null) {
            return;
        }
        for (int i = 0; i < mFuncViewArrayMap.size(); i++) {
            int keyTemp = mFuncViewArrayMap.keyAt(i);
            if (keyTemp == key) {
                mFuncViewArrayMap.get(keyTemp).setVisibility(VISIBLE);
            } else {
                mFuncViewArrayMap.get(keyTemp).setVisibility(GONE);
            }
        }
        mCurrentFuncKey = key;
        setVisibility(true);

        if (mOnFuncChangeListener != null) {
            mOnFuncChangeListener.onFuncChange(mCurrentFuncKey);
        }
    }
    public int getCurrentFuncKey() {
        return mCurrentFuncKey;
    }

    private OnFuncChangeListener mOnFuncChangeListener;

    public interface OnFuncChangeListener {
        void onFuncChange(int key);
    }

    public void setOnFuncChangeListener(OnFuncChangeListener listener) {
        this.mOnFuncChangeListener = listener;
    }

    private List<OnFuncKeyBoardListener> mListenerList;

    public void addOnKeyBoardListener(OnFuncKeyBoardListener mOnFuncChangeListener) {
        if (mListenerList == null) {
            mListenerList = new ArrayList<>();
        }
        mListenerList.add(mOnFuncChangeListener);
    }

    public interface OnFuncKeyBoardListener {
        /**
         * 功能布局弹起
         */
        void OnFuncPop(int height);

        /**
         * 功能布局关闭
         */
        void OnFuncClose();
    }

    public void updateHeight(int height) {
        this.mHeight = height;
    }

    public boolean isOnlyShowSoftKeyboard() {
        return mCurrentFuncKey == DEF_KEY;
    }
}
