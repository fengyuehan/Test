package com.example.timepicker.calendar.util;

import android.content.Context;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author : zhangzf
 * date   : 2020/11/30
 * desc   :
 */
public class TimeUtil {
    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 得到某年某月一号是星期几  （0-6 日-六）
     */
    public static Integer getWeekdayOfMonth(int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = null;
        try {
            dt = (Date) sdf.parse(year + "-" + month + "-" + day + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }

    /**
     * 得到当前年份当前月份天数
     */
    public static int getMonthOfDay(int year, int month) {
        int day = 0;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            day = 29;
        } else {
            day = 28;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return day;

        }
        return 0;
    }

    /**
     * 得到屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    public static int getNowYear(int i){
        if (i>=0 && i<12){
            return 0;
        }else if (i>=12 && i<24){
            return 1;
        }else if (i>=24 && i<36){
            return 2;
        }else if (i>=48 && i<60){
            return 3;
        }else if (i>=60 && i<72){
            return 4;
        }else if (i>=72 && i<84){
            return 5;
        }else if (i>=84 && i<96){
            return 6;
        }else if (i>=96 && i<108){
            return 7;
        }else if (i>=108 && i<120){
            return 8;
        }else{
            throw new IndexOutOfBoundsException("倍数设置不能超过4");
        }
    }
}
