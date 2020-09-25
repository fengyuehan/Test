package com.example.customview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ScrollActivity extends AppCompatActivity {
    private TextView textView;
    private Button mScrollTo,mScrollBy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        textView = findViewById(R.id.tv);
        mScrollTo = findViewById(R.id.btn_scroll_to);
        mScrollBy = findViewById(R.id.btn_scroll_by);

        /**
         * 从结果来看，scrollTo和scrollBy传入的参数为正时，则往负方向移动，反之则向正方向移动
         * 如果该View没有子View，那么移动的就是View的内容。如果该View中有子View，那么移动的就是子View
         * scrollTo不管点击多少次，都只执行一次，scrollTo()的偏移位置是相对于初始位置的偏移位置
         * scrollBy则点一次执行一次。scrollBy()的偏移位置是相对于当前位置的偏移位置。
         */
        mScrollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.scrollTo(10,10);
            }
        });
        mScrollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.scrollBy(10,10);
            }
        });
    }
}
