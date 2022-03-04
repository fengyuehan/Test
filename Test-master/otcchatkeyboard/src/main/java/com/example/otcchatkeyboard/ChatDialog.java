package com.example.otcchatkeyboard;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ChatDialog extends Dialog {
    private Context mContext;

    private TextView mCopy;

    private CallBack mCallBack;

    public ChatDialog(Context context,CallBack mCallBack) {
        this(context,0,mCallBack);
        this.mContext = context;
        this.mCallBack = mCallBack;
    }

    public ChatDialog(Context context, int themeResId,CallBack mCallBack) {
        super(context, R.style.CustomDialog);
        this.mContext = context;
        this.mCallBack = mCallBack;
        initDialog();
    }

    private void initDialog() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_copy, null);
        setContentView(contentView);
        mCopy = contentView.findViewById(R.id.tv_copy);
        mCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallBack != null){
                    mCallBack.callBack();
                    dismiss();
                }
            }
        });
    }

    public interface CallBack{
        void callBack();
    }
}
