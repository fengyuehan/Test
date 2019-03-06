package com.example.scrollview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mTitles;

    public MyAdapter(Context mContext) {
        this.mContext = mContext;
        this.mTitles  = new ArrayList<>();
        for (int i = 0;i < 50;i++){
            int index = i+1;
            mTitles.add("item" + index);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext,R.layout.item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mTitles.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    public void addItem(List<String> newDatas){
        newDatas.addAll(mTitles);
        mTitles.removeAll(mTitles);
        mTitles.addAll(newDatas);
        notifyDataSetChanged();

    }
    public void addMoreItem(List<String> newDatas){
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }
}
