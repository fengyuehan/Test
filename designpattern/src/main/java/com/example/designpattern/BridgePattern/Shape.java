package com.example.designpattern.BridgePattern;

public abstract class Shape {
    /**
     * 桥接模式
     */
    private Color mColor;
    public void setmColor(Color mColor){
        this.mColor = mColor;
    }

    public abstract String shape();
}
