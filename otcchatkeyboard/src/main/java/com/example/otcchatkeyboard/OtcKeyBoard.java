package com.example.otcchatkeyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class OtcKeyBoard extends AutoHeightLayout implements View.OnClickListener, OtcEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener {
    public static final int FUNC_TYPE_EMOTION = -1;
    public static final int FUNC_TYPE_APPPS = -2;

    protected OtcEditText mEtChat;
    protected ImageView mMutilOrSend;
    protected FuncLayout mLyKvml;

    protected boolean mDispatchKeyEventPreImeLock = false;

    public OtcKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        View view = View.inflate(context,R.layout.view_key_board, this);
        initView(view);
        initEditView();
    }

    private void initEditView() {
        mEtChat.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mEtChat.isFocused()) {
                    mEtChat.setFocusable(true);
                    mEtChat.setFocusableInTouchMode(true);
                }
                return false;
            }
        });

        mEtChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    mMutilOrSend.setImageDrawable(getResources().getDrawable(R.drawable.icon_send));
                    mMutilOrSend.setTag(2);
                } else {
                    mMutilOrSend.setImageDrawable(getResources().getDrawable(R.drawable.icon_add));
                    mMutilOrSend.setTag(1);
                }
            }
        });
    }

    private void initView(View view) {
        mEtChat = view.findViewById(R.id.et_chat);
        mLyKvml = view.findViewById(R.id.ly_kvml);
        mMutilOrSend = view.findViewById(R.id.iv_multi_and_send);
        mEtChat.setOnBackKeyClickListener(this);
        mMutilOrSend.setOnClickListener(this);
        mMutilOrSend.setImageDrawable(getResources().getDrawable(R.drawable.icon_add));
        mMutilOrSend.setTag(1);
    }


    public void addFuncView(View view) {
        mLyKvml.addFuncView(FUNC_TYPE_APPPS, view);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_multi_and_send:
                if (mMutilOrSend.getTag() != null){
                    if ((int)mMutilOrSend.getTag() == 1){
                        toggleFuncView(FUNC_TYPE_APPPS);
                    }else if((int)mMutilOrSend.getTag() == 2){
                        String message = mEtChat.getText().toString();
                        mEtChat.setText("");

                        if (mOnEtChatListener != null){
                            mOnEtChatListener.etChatListener(message);
                        }
                    }
                }
                break;
        }
    }

    protected void setFuncViewHeight(int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLyKvml.getLayoutParams();
        params.height = height;
        mLyKvml.setLayoutParams(params);
    }

    private void toggleFuncView(int key) {
        mMutilOrSend.setImageDrawable(getResources().getDrawable(R.drawable.icon_add));
        mLyKvml.toggleFuncView(key, isSoftKeyboardPop(), mEtChat);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        mLyKvml.updateHeight(height);
    }

    @Override
    public void onFuncChange(int key) {

    }

    @Override
    public void onBackKeyClick() {
        if (mLyKvml.isShown()) {
            mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    public void reset() {
        KeyboardUtils.closeSoftKeyboard(this);
        mLyKvml.hideAllFuncView();
    }

    @Override
    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        mLyKvml.setVisibility(true);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (mLyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener l) {
        mLyKvml.addOnKeyBoardListener(l);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mDispatchKeyEventPreImeLock) {
                    mDispatchKeyEventPreImeLock = false;
                    return true;
                }
                if (mLyKvml.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (KeyboardUtils.isFullScreen((Activity) getContext())) {
            return false;
        }
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (KeyboardUtils.isFullScreen((Activity) getContext())) {
            return;
        }
        super.requestChildFocus(child, focused);
    }

    public boolean dispatchKeyEventInFullScreen(KeyEvent event) {
        if(event == null){
            return false;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (KeyboardUtils.isFullScreen((Activity) getContext()) && mLyKvml.isShown()) {
                    reset();
                    return true;
                }
            default:
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    boolean isFocused;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        isFocused = mEtChat.getShowSoftInputOnFocus();
                    } else {
                        isFocused = mEtChat.isFocused();
                    }
                    if(isFocused){
                        mEtChat.onKeyDown(event.getKeyCode(), event);
                    }
                }
                return false;
        }
    }

    public OtcEditText getEtChat(){
        return mEtChat;
    }

    public ImageView getmMutilOrSend(){
        return mMutilOrSend;
    }
    private OnEtChatListener mOnEtChatListener;

    public void setmOnEtChatListener(OnEtChatListener mOnEtChatListener) {
        this.mOnEtChatListener = mOnEtChatListener;
    }

    public interface OnEtChatListener{
        void etChatListener(String message);
    }
}
