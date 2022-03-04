package com.example.litepaldemo.bean;

import org.litepal.crud.DataSupport;

public class CommentBean extends DataSupport {
    private int id;
    private long time;//评论时间
    private String nickname;//评论昵称
    private String content;//评论内容
    private NewsBean newsBean;//一条评论对应一条新闻

    public CommentBean(long currentTimeMillis, String str, String s) {
        this.time = currentTimeMillis;
        this.nickname = str;
        this.content = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsBean getNewsBean() {
        return newsBean;
    }

    public void setNewsBean(NewsBean newsBean) {
        this.newsBean = newsBean;
    }
}
