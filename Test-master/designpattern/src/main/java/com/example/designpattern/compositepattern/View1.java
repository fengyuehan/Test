package com.example.designpattern.compositepattern;

public class View1 extends Component {
    private int width;
    private int height;

    public View1(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    public void display() {
        System.out.println("我是按钮，宽：" + width + "，高：" + height);
    }
}
