package com.example.designpattern.ObserverPattern;

import java.util.Vector;

public class Secretary implements Subject {
    private Vector<Observer> vector = new Vector<>();
    private String action;

    @Override
    public void attach(Observer observer) {
        vector.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        vector.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer:vector){
            observer.updata();
        }
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getAction() {
        return action;
    }
}
