package com.example.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


/**
 * @ClassName LoadingDialog
 * @Description TODO
 * @Author user
 * @Date 2019/9/20
 * @Version 1.0
 */
public class LoadingDialog extends Dialog {
    private Context mContext;
    private LoadingDataView mLoadingDataView;

    public LoadingDialog(Context context){
        super(context, R.style.CustomDialog);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        super.setContentView(view);
        setCanceledOnTouchOutside(false);
        mLoadingDataView = findViewById(R.id.view);
        mLoadingDataView.setDuration(0.01f);
        mLoadingDataView.setLineLength(0f);
        mLoadingDataView.start();
    }

    public void stopLoading(){
        mLoadingDataView.reset();
        dismiss();
    }
}
