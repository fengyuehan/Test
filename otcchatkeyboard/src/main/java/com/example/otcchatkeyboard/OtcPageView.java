package com.example.otcchatkeyboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class OtcPageView extends RelativeLayout {
    private GridView mGridView;

    public GridView getGridView(){
        return mGridView;
    }

    public OtcPageView(Context context) {
        this(context, null);
    }

    public OtcPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_page, this);
        mGridView = (GridView) view.findViewById(R.id.gv_emotion);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            mGridView.setMotionEventSplittingEnabled(false);
        }
        mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        mGridView.setCacheColorHint(0);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mGridView.setVerticalScrollBarEnabled(false);
    }

    public void setNumColumns(int row) {
        mGridView.setNumColumns(row);
    }
}
