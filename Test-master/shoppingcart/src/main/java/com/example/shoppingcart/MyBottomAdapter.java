package com.example.shoppingcart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidbase.widget.recycleadpter.BaseRecycleViewAdapter;


public class MyBottomAdapter extends BaseRecycleViewAdapter<ShopCar> {
    private Context mContext;
    int num = 1;
    OnItemClickListener mOnItemClickListener;
    RecyclerView.LayoutParams layoutParams;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public MyBottomAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onBindBaseViewHolder(RecyclerView.ViewHolder holder, ShopCar item) {
        layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_store_name.setText(item.getName());
        myViewHolder.tv_store_money.setText("" + item.getPrice());
        myViewHolder.tv_count.setText("" + num);
        myViewHolder.iv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 1){
                    num--;
                    myViewHolder.tv_count.setText("" + num);
                }else {
                    int position = layoutParams.getViewLayoutPosition();
                    notifyItemRemoved(position);
                }

            }
        });
        myViewHolder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num < Integer.parseInt(item.getStockCount())){
                    num ++;
                }
                myViewHolder.tv_count.setText("" + num);
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(View.inflate(mContext,R.layout.item_bottom,null));
    }

    @Override
    protected int getBaseItemViewType(int position) {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_store_name,tv_store_money,tv_count;
        private ImageView iv_jian,iv_add;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_store_name = itemView.findViewById(R.id.tv_store_name);
            tv_store_money = itemView.findViewById(R.id.tv_store_money);
            tv_count = itemView.findViewById(R.id.tv_count);
            iv_jian = itemView.findViewById(R.id.iv_jian);
            iv_add = itemView.findViewById(R.id.iv_add);
        }
    }

    interface OnItemClickListener{
        void onItemClick();
    }
}
