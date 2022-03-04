package com.example.designpattern.compositepattern2;

public class ViewB implements Component {
    private int height;
    private int width;

    public ViewB(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public void addComponent(Component component) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void removeComponent(Component component) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void display() {
        System.out.println("我是文本框，宽：" + width + "，高：" + height);
    }
}
