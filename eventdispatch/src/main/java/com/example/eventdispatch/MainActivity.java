package com.example.eventdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "zzf";
    private Button button,button1;
    private MyLinearLayout myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button1 = findViewById(R.id.btn1);
        myLinearLayout = findViewById(R.id.ll_my_layout);
        myLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zzf","ViewGroup onClick");
            }
        });

        myLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("zzf","viewGroup onTouch");
                return false;
            }
        });
        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("zzf","OnTouch -----"+event.getAction());
                return false;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zzf","view  onClick");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("zzf","onClick");
            }
        });

        /**
         * 当return flase时
         * 2020-06-23 21:53:53.258 3812-3812/com.example.eventdispatch E/zzf: OnTouch -----0
         * 2020-06-23 21:53:53.385 3812-3812/com.example.eventdispatch E/zzf: OnTouch -----1
         * 2020-06-23 21:53:53.385 3812-3812/com.example.eventdispatch E/zzf: onClick
         *
         * onClick事件是在onTouchEvent的ACTION_UP事件中，如果dispatchTouchEvent返回false,则会走onTouchEvent，只要保证我们mOnClickListener
         * 不为null，则会走，什么情况下不为null,就是setOnClickListener的时候。如果dispatchTouchEvent返回true时，直接return，所以没有onClick打印出来。
         *
         *
         * 当dispatchTouchEvent在进行事件分发的时候，只有前一个action返回true，才会触发后一个action。
         *
         *
         * return true
         *2020-06-23 21:55:21.795 4406-4406/com.example.eventdispatch E/zzf: OnTouch -----0
         * 2020-06-23 21:55:21.917 4406-4406/com.example.eventdispatch E/zzf: OnTouch -----1
         *
         */
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("zzf","OnTouch -----"+event.getAction());
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "=================Activity dispatchTouchEvent Action: "
                + Util.getAction(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("zzf", "=================Activity dispatchTouchEvent Action: "
                + Util.getAction(event));
        return super.onTouchEvent(event);
    }
}
