package com.example.shoppingcart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidbase.widget.recycleadpter.BaseRecycleViewAdapter;

public class MyLeftAdapter extends BaseRecycleViewAdapter<ShopCarLeftBean> {
    private Context mContext;

    public MyLeftAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    protected void onBindBaseViewHolder(RecyclerView.ViewHolder holder, ShopCarLeftBean item) {
        MyLeftViewHolder myLeftViewHolder = (MyLeftViewHolder) holder;
        myLeftViewHolder.textView.setText(item.getName());
    }

    @Override
    protected RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new MyLeftViewHolder(View.inflate(mContext,R.layout.item_left,null));
    }

    @Override
    protected int getBaseItemViewType(int position) {
        return 0;
    }

    class MyLeftViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyLeftViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
