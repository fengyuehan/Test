package com.example.slidingverification;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mingle.widget.LoadingView;
import com.mingle.widget.ShapeLoadingView;

import me.wangyuwei.slackloadingview.SlackLoadingView;

/**
 * @ClassName LoadingDialog
 * @Description TODO
 * @Author user
 * @Date 2019/9/20
 * @Version 1.0
 */
public class LoadingDialog extends Dialog {
    private Context mContext;
    private SlackLoadingView mSlackLoadingView;

    public LoadingDialog(Context context){
        super(context);
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        super.setContentView(view);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.50); //设置宽度
        lp.height = (int) (display.getHeight()*0.25);
        this.getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(false);
        mSlackLoadingView = view.findViewById(R.id.shapeLoadingView);
        mSlackLoadingView.setDuration(0.01f);
        mSlackLoadingView.setLineLength(0.2f);
        mSlackLoadingView.start();
    }

    public void stopLoading(){
        mSlackLoadingView.reset();
        dismiss();
    }
}
