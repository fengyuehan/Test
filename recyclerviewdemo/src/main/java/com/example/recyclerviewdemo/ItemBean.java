package com.example.recyclerviewdemo;

import java.util.List;


public class ItemBean {
    private String title;
    private List<String> subTitle;
    public int scrollOffset;
    public int scrollPosition;

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(List<String> subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public String toString() {
        return "ItemBean{" +
                "title='" + title + '\'' +
                ", subTitle=" + subTitle +
                '}';
    }
}
