package com.example.designpattern.compositepattern;

import java.util.ArrayList;
import java.util.List;

public class ViewGroupA extends Component {
    private List<Component> list;
    private int width;
    private int height;

    public ViewGroupA(int width, int height) {
        super(width, height);
        this.width = width;
        this.height = height;
        list = new ArrayList<>();
    }

    @Override
    public void display() {
        System.out.println("我是容器，宽：" + width + "，高：" + height + "，里面有" + list.size() + "个子视图");
        for (Component view : list) {
            view.display();
        }
    }

    public void addComponent(Component component){
        if (component != null){
            list.add(component);
        }
    }

    public void removeComponent(Component component){
        if (component != null){
            list.remove(component);
        }
    }
}
