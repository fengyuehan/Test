package com.example.contentprovider.sortlistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.contentprovider.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements SectionIndexer {

    private Context mContext;
    private List<SortModel> datas;
    private OnItemListener mOnItemListener;

    public MyAdapter(Context mContext, List<SortModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    public void updateData(List<SortModel> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public String getName(int position){
        return datas.get(position).getName();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.recycler_item_1,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        SortModel content = datas.get(position);
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)){
            holder.letter.setVisibility(View.VISIBLE);
            holder.letter.setText(content.getSortLetters());
        }else {
            holder.letter.setVisibility(View.GONE);
        }
        holder.name.setText(datas.get(position).getName());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemListener != null){
                    mOnItemListener.itemListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getItemCount(); i++){
            String sortStr = datas.get(i).getSortLetters();
            char firstChar = sortStr.toLowerCase().charAt(0);
            if (firstChar == sectionIndex){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return datas.get(position).getSortLetters().charAt(0);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name,letter;
        private LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            letter = itemView.findViewById(R.id.tv_letter);
            ll = itemView.findViewById(R.id.ll_item);
        }
    }

    public interface OnItemListener{
        void itemListener(int position);
    }
}
