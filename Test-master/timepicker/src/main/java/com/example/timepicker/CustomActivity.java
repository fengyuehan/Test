package com.example.timepicker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnCustomTimeSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.CustomTimePickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author : zhangzf
 * date   : 2020/11/28
 * desc   :
 */
public class CustomActivity extends AppCompatActivity {
    private CustomTimePickerView pvTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_main);

        initTimePicker();
    }

    private void initTimePicker() {
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);

        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 11, 31);

        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnCustomTimeSelectListener() {
            @Override
            public void onCustomTimeSelect(String time1, String time2, View v) {
                Log.e("zzf",time1 + "----" + time2);
                Toast.makeText(CustomActivity.this,time1 + "----" + time2,Toast.LENGTH_LONG).show();
            }
        })
                .setType(new boolean[]{false, true, true, false, true, true}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("", "月", "日", "", "月", "日")//默认设置为年月日时分秒
                .isCenterLabel(false)
                .setDividerColor(Color.RED)
                .setTextColorCenter(Color.RED)//设置选中项的颜色
                .setTextColorOut(Color.BLUE)//设置没有被选中项的颜色
                .isCyclic(true)
                .setItemVisibleCount(5)
                .setSubmitText("确定")
                .setCancelText("取消")
                .setDate(selectedDate)
                .setLineSpacingMultiplier(2f)
                .setTextXOffset(0, 0,0, 0, 0, 0)//设置X轴倾斜角度[ -90 , 90°]
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        pvTime.show();
    }

}
