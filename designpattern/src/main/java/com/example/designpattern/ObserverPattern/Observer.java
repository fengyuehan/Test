package com.example.designpattern.ObserverPattern;

public abstract class Observer {
    /**
     * 观察者模式
     */
    private String name;
    private Subject subject;

    public Observer(String name,Subject subject){
        this.name = name;
        this.subject = subject;
    }

    public abstract void updata();
}
