package com.example.designpattern.DecoratorPattern;

public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape mShape) {
        super(mShape);
    }

    @Override
    public void draw() {
        super.draw();
        mShape.draw();
        setRedBorder(mShape);
    }

    private void setRedBorder(Shape mShape) {
        System.out.println("Border Color: Red");
    }
}
