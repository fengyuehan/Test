package com.example.timepicker.calendar.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timepicker.R;
import com.example.timepicker.calendar.listener.OnMonthClickListener;
import com.kproduce.roundcorners.RoundTextView;

import java.util.List;

/**
 * author : zhangzf
 * date   : 2020/11/30
 * desc   :
 */
public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthViewHolder> {

    private List<String> mList;
    private int mIndex = 0;
    private OnMonthClickListener mOnMonthClickListener;

    public MonthAdapter(List<String> mList) {
        this.mList = mList;
    }

    public void setOnMonthClickListener(OnMonthClickListener mOnMonthClickListener) {
        this.mOnMonthClickListener = mOnMonthClickListener;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.month_item,null);
        MonthViewHolder viewHolder = new MonthViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, final int position) {
        holder.tv_month.setText(mList.get(position));
        holder.tv_month.setTextColor(Color.parseColor("#000000"));
        holder.rtv_month.setVisibility(View.GONE);
        if (mIndex == position){
            holder.tv_month.setTextColor(Color.parseColor("#74C02C"));
            holder.rtv_month.setVisibility(View.VISIBLE);
            holder.rtv_month.setBackgroundColor(Color.parseColor("#74C02C"));
        }
        holder.tv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIndex = position;
                if (mOnMonthClickListener != null){
                    mOnMonthClickListener.onClick(mIndex);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int mIndex) {
        this.mIndex = mIndex;
        notifyDataSetChanged();
    }

    static class MonthViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_month;
        private RoundTextView rtv_month;

        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_month = itemView.findViewById(R.id.tv_month);
            rtv_month = itemView.findViewById(R.id.rtv_month);
        }
    }
}
