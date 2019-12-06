package com.example.greendao;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidbase.widget.recycleadpter.BaseRecycleViewAdapter;

public class GreenDaoAdapter extends BaseRecycleViewAdapter<User> {

    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public GreenDaoAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    protected void onBindBaseViewHolder(RecyclerView.ViewHolder holder, User item) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mTextView.setText(item.getName());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(myViewHolder.itemView,item);
            }
        });
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemLongClickListener.onLongClick(myViewHolder.itemView,item);
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext,R.layout.search_page_flowlayout_tv,null));
    }

    @Override
    protected int getBaseItemViewType(int position) {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.flowlayout_tv);
        }
    }

    interface OnItemClickListener {
        void onItemClick(View view, User item);
    }

    interface OnItemLongClickListener {
        void onLongClick(View view,User item);
    }

}
