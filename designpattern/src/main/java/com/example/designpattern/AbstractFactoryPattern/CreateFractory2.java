package com.example.designpattern.AbstractFactoryPattern;

public class CreateFractory2 extends AbstractFactory {
    @Override
    public AbstractProductA abstractProductA() {
        return new CreateProductA2();
    }

    @Override
    public AbstractProductB abstractProductB() {
        return new CreateProductB2();
    }
}
