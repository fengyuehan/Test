package com.example.otcchatkeyboard;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ChatPop extends PopupWindow {

    private Context mContext;
    private CallBack mCallBack;
    private TextView mCopy;
    private int popupHeight;
    private int popupWidth;

    public ChatPop(Context context,CallBack mCallBack){
        super(context);
        this.mContext = context;
        this.mCallBack = mCallBack;
        initView(context);

    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_copy, null);
        setFocusable(true);
        setAnimationStyle(R.style.CustomPopWindowStyle);
        setBackgroundDrawable(new ColorDrawable(0));
        setHeight(44);
        setWidth(130);
        setOutsideTouchable(true);
        setContentView(contentView);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = contentView.getHeight();
        popupWidth = contentView.getWidth();
        Log.e("ZZF",popupHeight + " --------- " + popupWidth);
        mCopy = contentView.findViewById(R.id.tv_copy);
        mCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallBack != null){
                    mCallBack.callBack(popupHeight,popupWidth);
                    dismiss();
                }
            }
        });
    }


    public interface CallBack{
        void callBack(int popupHeight,int popupWidth);
    }

}
