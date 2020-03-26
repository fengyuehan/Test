package com.example.designpattern.proxyPattern;

public class Friend implements Collectable {
    private String name;

    public Friend(String name){
        this.name = name;
    }

    @Override
    public void collectPack() {
        System.out.println(this.name + "去取快递...");
    }
}
