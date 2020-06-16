package com.example.spiner;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public class PopDialog extends Dialog {
    private List<String> mData;
    RecyclerView recyclerView;
    SpinerAdapter mSpinerAdapter;
    OnListener mOnListener;

    public PopDialog(@NonNull Context context,List<String> mData,OnListener mOnListener) {
        super(context);
        this.mData = mData;
        this.mOnListener = mOnListener;
        initDialog(context);
    }

    private void initDialog(Context context) {
        View view = View.inflate(context,R.layout.dialog_item,null);
        setContentView(view);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.78); //设置宽度
        lp.height = (int) (display.getHeight()*0.6);
        this.getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);
        recyclerView = view.findViewById(R.id.rv);
        initRecyclerView(context);
    }

    private void initRecyclerView(Context context) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mSpinerAdapter = new SpinerAdapter(mData);
        recyclerView.setAdapter(mSpinerAdapter);
        mSpinerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String str = mData.get(position);
                if (mOnListener != null){
                    mOnListener.callBack(str);
                    dismiss();
                }
            }
        });
    }
}
