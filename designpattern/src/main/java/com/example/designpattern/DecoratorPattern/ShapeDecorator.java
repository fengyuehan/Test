package com.example.designpattern.DecoratorPattern;

public abstract class ShapeDecorator implements Shape {
    protected Shape mShape;

    public ShapeDecorator(Shape mShape){
        this.mShape = mShape;
    }

    public void draw(){
        mShape.draw();
    }
}
