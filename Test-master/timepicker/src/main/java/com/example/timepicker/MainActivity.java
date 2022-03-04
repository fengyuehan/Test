package com.example.timepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.pickerview.view.TimePickerView;
import com.example.customdatelibrary.ZhxDate;
import com.example.timepicker.calendar.MultiFunctionCalendarView;
import com.example.timepicker.calendar.listener.OnCalendarDoubleSelectListener;
import com.example.timepicker.calendar.listener.OnDoubleSelectListener;

public class MainActivity extends AppCompatActivity {

    private TimePickerView pvTime;
    private Button button;
    private ZhxDate zhxDate;
    private MultiFunctionCalendarView mfcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        /*zhxDate = findViewById(R.id.zhxdate);
        zhxDate.setType(3);*/
        mfcv = findViewById(R.id.mfcv);
        mfcv.setType(2);
        mfcv.setOnDoubleSelectListener(new OnDoubleSelectListener() {
            @Override
            public void onDoubleSelect(String date1, String date2) {
                Toast.makeText(MainActivity.this,date1 + " -----" +date2,Toast.LENGTH_LONG).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CustomActivity.class));
            }
        });
    }


}
