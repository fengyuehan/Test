package com.example.designpattern.compositepattern2;

import java.util.ArrayList;
import java.util.List;

public class ViewGroupA implements Component {
    private int weigth;
    private int width;
    private List<Component> list = new ArrayList<>();

    public ViewGroupA(int weigth, int width) {
        this.weigth = weigth;
        this.width = width;
    }

    @Override
    public void addComponent(Component component) {
        if (component != null){
            list.add(component);
        }
    }

    @Override
    public void removeComponent(Component component) {
        if (component != null){
            list.remove(component);
        }
    }

    @Override
    public void display() {
        System.out.println("我是容器，宽：" + width + "，高：" + weigth + "，里面有" + list.size() + "个子视图");
        for (Component view : list) {
            view.display();
        }
    }
}
