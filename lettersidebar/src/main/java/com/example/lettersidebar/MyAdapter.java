package com.example.lettersidebar;

import android.content.Context;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * @author zzf
 * @date 2019/7/23/023
 * 描述：
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context mContext;
    private List<ItemBean> mTitles;

    public MyAdapter(Context mContext, List<ItemBean> mTitles) {
        this.mContext = mContext;
        this.mTitles = mTitles;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext,R.layout.item_letter,null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder myViewHolder = holder;
        myViewHolder.textView.setText(mTitles.get(position).getName());
        myViewHolder.textView2.setText("(" + mTitles.get(position).getName() + ")");
        String text = mTitles.get(position).getName().substring(0,1);
        myViewHolder.myView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView,textView2;
        private MyView myView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
            textView2 = itemView.findViewById(R.id.tv);
            myView = itemView.findViewById(R.id.mv);
        }
    }
}
