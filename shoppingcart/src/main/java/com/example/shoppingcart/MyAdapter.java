package com.example.shoppingcart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidbase.widget.recycleadpter.BaseRecycleViewAdapter;
import com.example.shoppingcart.greendao.ShopCarDao;

import java.util.List;

public class MyAdapter extends BaseRecycleViewAdapter<ShopCar> {
    Context mContext;
    int num;
    int count;

    public MyAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    protected void onBindBaseViewHolder(RecyclerView.ViewHolder holder, ShopCar item) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.shop_name.setText(item.getName());
        myViewHolder.shop_stockCount.setText(item.getStockCount());
        myViewHolder.shop_money.setText("" + item.getPrice());
        count = Integer.parseInt(item.getStockCount());
        myViewHolder.shop_num.setText("" + num);
        myViewHolder.shop_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num < item.getNum()){
                    num++;
                }
                myViewHolder.shop_num.setText("" + num);
                myViewHolder.shop_stockCount.setText("" + (count - num));
            }
        });
        myViewHolder.shop_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 0){
                    num--;
                }
                myViewHolder.shop_num.setText("" + num);
                myViewHolder.shop_stockCount.setText("" + (count - num));
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext,R.layout.item_shoplist_right,null));
    }

    @Override
    protected int getBaseItemViewType(int position) {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView shop_name;
        private TextView shop_money;
        private TextView shop_stockCount;
        private ImageView shop_jian;
        private TextView shop_num;
        private ImageView shop_add;
        public MyViewHolder(View itemView) {
            super(itemView);
            shop_name = itemView.findViewById(R.id.shop_name);
            shop_money = itemView.findViewById(R.id.shop_money);
            shop_stockCount = itemView.findViewById(R.id.shop_stockCount);
            shop_jian = itemView.findViewById(R.id.shop_jian);
            shop_num = itemView.findViewById(R.id.shop_num);
            shop_add = itemView.findViewById(R.id.shop_add);
        }
    }

}
