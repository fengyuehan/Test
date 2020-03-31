package com.example.litepaldemo.bean;



import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Category extends DataSupport {
    private int id;
    private String name;
    private List<NewsBean> list=new ArrayList<>();//一条新闻可能有多个种类、一个种类包含多条新闻，需要一张外表保存

    public Category(String name) {
        this.name = name;
    }

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

    public List<NewsBean> getList() {
        return list;
    }

    public void setList(List<NewsBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}
