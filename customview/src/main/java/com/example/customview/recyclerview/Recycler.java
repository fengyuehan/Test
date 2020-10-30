package com.example.customview.recyclerview;

import android.view.View;

import java.util.Stack;

public class Recycler {
    private Stack<View>[] views;

    public Recycler(int typeCount){
        views = new Stack[typeCount];
        for (int i = 0;i < typeCount; i++){
            views[i] = new Stack<View>();
        }
    }

    public View getView( int type){
        try {
            return views[type].pop();
        }catch (Exception e){
            return null;
        }
    }

    public void addView(int type,View view){
        views[type].push(view);
    }
}
