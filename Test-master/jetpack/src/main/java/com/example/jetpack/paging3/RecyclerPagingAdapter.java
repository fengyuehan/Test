package com.example.jetpack.paging3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jetpack.R;

public class RecyclerPagingAdapter extends PagedListAdapter<Student, RecyclerView.ViewHolder> {

    private static DiffUtil.ItemCallback<Student> DIFF_STUDENT = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.equals(newItem);
        }
    };

    protected RecyclerPagingAdapter() {
        super(DIFF_STUDENT);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Student student = getItem(position);
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        if (student == null){
            recyclerViewHolder.tvId.setText("加载中");
            recyclerViewHolder.tvName.setText("加载中");
            recyclerViewHolder.tvGender.setText("加载中");
        }else {
            recyclerViewHolder.tvId.setText(student.getId());
            recyclerViewHolder.tvName.setText(student.getName());
            recyclerViewHolder.tvGender.setText(student.getGender());
        }
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvId;
        TextView tvName;
        TextView tvGender;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            tvGender = itemView.findViewById(R.id.tvAge);
        }
    }
}
