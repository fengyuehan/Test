package com.example.designpattern.DynamicProxyPattern;

public class XiaoMing implements IRoom {
    @Override
    public void seekRoom() {
        System.out.println("找房");
    }

    @Override
    public void watchRoom() {
        System.out.println("看房");
    }

    @Override
    public void room() {
        System.out.println("给钱租房");
    }

    @Override
    public void finish() {
        System.out.println("完成租房");
    }
}
