package com.example.timepicker.calendar.bean;

/**
 * author : zhangzf
 * date   : 2020/12/1
 * desc   :
 */
public class CustomDateBean {
    private String olddate; //需要覆盖的原数据
    private String newdata; //新数据 覆盖的数据

    public CustomDateBean(){

    }

    public CustomDateBean(String olddate, String newdata) {
        this.olddate = olddate;
        this.newdata = newdata;
    }

    public String getOlddate() {
        return olddate;
    }

    public void setOlddate(String olddate) {
        this.olddate = olddate;
    }

    public String getNewdata() {
        return newdata;
    }

    public void setNewdata(String newdata) {
        this.newdata = newdata;
    }
}
