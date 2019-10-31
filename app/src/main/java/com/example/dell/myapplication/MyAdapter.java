package com.example.dell.myapplication;

import android.content.Context;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    List<Integer> datas;

    private OnItemClickListener mOnItemClickListener;
    private List<Integer> heights = new ArrayList<>();
    private int type = 0;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void addData(int index, int data) {
        /*this.datas.add(index, data);
        int height = (int) (Math.random() * 100 + 300);
        heights.add(index, height);*/
        addData(index,data,1);

    }

    public void addData(int index,int data,int count){
        for (int i = 0;i<count;i++) {
            this.datas.add(index, data);
            int height = (int) (Math.random() * 100 + 300);
            heights.add(index, height);
        }
        notifyDataSetChanged();
    }

    public void removeData(int index){
        this.datas.remove(index);
        notifyDataSetChanged();
    }

    public void setDatas(List<Integer> datas) {
        this.datas = datas;
        for (int height : datas) {
            height = (int) (Math.random() * 100 + 300);
            heights.add(height);
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        RecyclerView.LayoutParams mLayoutParams;
        if (type == 0) {
            mLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (type == 1) {
            mLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            mLayoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heights.get(position));
            mLayoutParams.setMargins(2, 2, 2, 2);
        }
        holder.itemView.setLayoutParams(mLayoutParams);
        holder.iv.setImageResource(datas.get(position));
        holder.textView.setText("分类" + position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(position, datas.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, Integer data);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}
