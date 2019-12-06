package com.example.flowlayout;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidbase.widget.recycleadpter.BaseRecycleViewAdapter;

public class FlowLayoutAdapter extends BaseRecycleViewAdapter<FlowLayoutBean> {

    private Context mContext;

    public FlowLayoutAdapter( Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    protected void onBindBaseViewHolder(RecyclerView.ViewHolder holder, FlowLayoutBean item) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv.setText(item.getTv());
    }

    @Override
    protected RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext,R.layout.item,null));
    }

    @Override
    protected int getBaseItemViewType(int position) {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
