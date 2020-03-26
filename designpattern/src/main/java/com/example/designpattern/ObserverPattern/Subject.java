package com.example.designpattern.ObserverPattern;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
    void setAction(String action);
    String getAction();
}
