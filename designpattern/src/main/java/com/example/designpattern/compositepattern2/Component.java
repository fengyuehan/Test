package com.example.designpattern.compositepattern2;

public interface Component {
    void addComponent(Component component);
    void removeComponent(Component component);
    void display();
}
