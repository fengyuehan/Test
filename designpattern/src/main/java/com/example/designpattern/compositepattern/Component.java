package com.example.designpattern.compositepattern;

public abstract class Component {
    private int width;
    private int height;

    public Component(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void display();
}
