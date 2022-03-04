package com.example.litepaldemo.bean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class NewsBean extends DataSupport {
    private int id;
    private String title;
    private String content;
    private long time;
    private List<CommentBean> list=new ArrayList<>();//一条新闻对应多条评论
    private List<Category>categoryList=new ArrayList<>();//一条新闻对应多个种类
    private IntroductionBean introductionBean;//一条新闻对应一个简洁

    public NewsBean(String title, String content, long time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public List<CommentBean> getList() {
        return list;
    }

    public void setList(List<CommentBean> list) {
        this.list = list;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public IntroductionBean getIntroductionBean() {
        return introductionBean;
    }

    public void setIntroductionBean(IntroductionBean introductionBean) {
        this.introductionBean = introductionBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
