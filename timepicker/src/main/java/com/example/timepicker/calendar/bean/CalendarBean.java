package com.example.timepicker.calendar.bean;

/**
 * author : zhangzf
 * date   : 2020/11/30
 * desc   :
 */
public class CalendarBean {
    private String date;
    private String note;

    public CalendarBean(String date, String note) {
        this.date = date;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
