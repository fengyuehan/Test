package com.example.designpattern.AbstractFactoryPattern;

public class CreateFactory1 extends AbstractFactory {
    @Override
    public AbstractProductA abstractProductA() {
        return new CreateProductA1();
    }

    @Override
    public AbstractProductB abstractProductB() {
        return new CreateProductB1();
    }
}
