package com.example.designpattern.ObserverPattern;

public class Test {
    public static void main(String[] args){
        Secretary secretary = new Secretary();
        NBAObserver nbaObserver = new NBAObserver("a",secretary);
        secretary.attach(nbaObserver);
        secretary.setAction("老板回来了");
        secretary.notifyObservers();
    }
}
