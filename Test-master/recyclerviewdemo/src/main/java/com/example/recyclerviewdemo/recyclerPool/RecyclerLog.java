package com.example.recyclerviewdemo.recyclerPool;

import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class RecyclerLog {

    private static final String TAG = "ZZF";

    public static void log(String log){
        Log.i(TAG,log);
    }

    public static void log(TextView textView,String log){
        if (textView.getText().length() != 0){
            StringBuilder stringBuilder = new StringBuilder(textView.getText());
            stringBuilder.append("\n").append(log);
            textView.setText(stringBuilder.toString());
        }else {
            textView.setText(log);
        }
    }

    public static void logPool(RecyclerView rcy) {
        RecyclerView.RecycledViewPool pool = rcy.getRecycledViewPool();
        Log.i(TAG, "当前pool里存在的Text:" + pool.getRecycledViewCount(0));
        while (logPoolVH(pool) != null) {

        }
    }

    private static Object logPoolVH(RecyclerView.RecycledViewPool pool) {
        RecyclerView.ViewHolder vh = pool.getRecycledView(0);
        if (vh != null) {
            Log.i(TAG, "Pool缓存中TextViewHolder位置：" + vh.getAdapterPosition());
        }
        return vh;
    }

    public static void loaAllCache(TextView tv, RecyclerView rcy) {
        try {
            Field mRecycler =
                    Class.forName("androidx.recyclerview.widget.RecyclerView").getDeclaredField("mRecycler");
            mRecycler.setAccessible(true);
            RecyclerView.Recycler recyclerInstance = (RecyclerView.Recycler) mRecycler.get(rcy);

            Class<?> recyclerClass = Class.forName(mRecycler.getType().getName());
            Field mViewCacheMax = recyclerClass.getDeclaredField("mViewCacheMax");
            Field mAttachedScrap = recyclerClass.getDeclaredField("mAttachedScrap");
            Field mChangedScrap = recyclerClass.getDeclaredField("mChangedScrap");
            Field mCachedViews = recyclerClass.getDeclaredField("mCachedViews");
            Field mRecyclerPool = recyclerClass.getDeclaredField("mRecyclerPool");
            mViewCacheMax.setAccessible(true);
            mAttachedScrap.setAccessible(true);
            mChangedScrap.setAccessible(true);
            mCachedViews.setAccessible(true);
            mRecyclerPool.setAccessible(true);

            int mViewCacheSize = (int) mViewCacheMax.get(recyclerInstance);
            ArrayListWrapper<RecyclerView.ViewHolder> mAttached =
                    (ArrayListWrapper<RecyclerView.ViewHolder>) mAttachedScrap.get(recyclerInstance);
            //ArrayListWrapper<RecyclerView.ViewHolder> mChanged =
                    //(ArrayListWrapper<RecyclerView.ViewHolder>) mChangedScrap.get(recyclerInstance);
            ArrayListWrapper<RecyclerView.ViewHolder> mCached =
                    (ArrayListWrapper<RecyclerView.ViewHolder>) mCachedViews.get(recyclerInstance);
            RecyclerView.RecycledViewPool recycledViewPool =
                    (RecyclerView.RecycledViewPool) mRecyclerPool.get(recyclerInstance);

            Class<?> recyclerPoolClass = Class.forName(mRecyclerPool.getType().getName());

            StringBuilder builder = new StringBuilder();
            builder.append("当前Attached数量：").append(mAttached.size()).append("\n");
            if (mAttached.size() > 0) {
                for (RecyclerView.ViewHolder vh : mAttached) {
                    builder.append(vh.getItemViewType() == 1 ? "Image" : "Text").append("-").append(vh.getLayoutPosition()).append("\n");
                }
            }
   //                 .append("当前Changed数量：").append(mChanged.size()).append("\n")
            builder .append("当前Cache的数量：").append(mCached.size()).append("\n");
            if (mCached.size() > 0) {
                for (RecyclerView.ViewHolder vh : mCached) {
                    builder.append(vh.getItemViewType() == 1 ? "Image" : "Text").append("-").append(vh.getLayoutPosition()).append("\n");
                }
            }
            builder.append("当前Pool中的TextViewHolder的数量：").append(recycledViewPool.getRecycledViewCount(0)).append("\n");
            builder.append("当前Pool中的ImgViewHolder的数量：").append(recycledViewPool.getRecycledViewCount(1));
            tv.setText(builder);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
