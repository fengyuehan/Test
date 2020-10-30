package com.example.customview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.recyclerview.RecyclerView;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    int count = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setAdapter(new MyAdapter(this));
    }


    private class MyAdapter implements RecyclerView.Adapter{

        private Context context;

        public MyAdapter(Context context){
            this.context = context;
        }

        @Override
        public View onCreateViewHolder(int position, View converView, ViewGroup parent) {
            if (converView == null){
                converView = LayoutInflater.from(context).inflate(R.layout.item_rv,parent,false);
            }
            return converView;
        }

        @Override
        public View onBinderViewHolder(int position, View converView, ViewGroup parent) {
            TextView textView = converView.findViewById(R.id.tv);
            textView.setText("第" + position +"行");
            return converView;
        }

        @Override
        public int getItemViewType(int row) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int getHeight(int index) {
            return 44;
        }
    }

}
