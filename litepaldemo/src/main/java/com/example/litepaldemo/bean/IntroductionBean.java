package com.example.litepaldemo.bean;

import org.litepal.crud.DataSupport;

public class IntroductionBean extends DataSupport {
    private int id;

    private String name;

    private NewsBean newsBean;//一个简介对应一条新闻

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
    }

    public  IntroductionBean(){

    }

    public IntroductionBean(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IntroductionBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", newsBean=" + newsBean +
                '}';
    }
}
