package com.example.designpattern.IteratorPattern;

/**
 * author : zhangzf
 * date   : 2021/2/3
 * desc   :
 */
public class Test {
    public static void main(String[] args){
        GroupLeader groupLeader = new GroupLeader();
        Director director = new Director();
        Manager manager = new Manager();
        Boss boss = new Boss();

        groupLeader.nextHandler = director;
        director.nextHandler = manager;
        manager.nextHandler = boss;
        groupLeader.handleReqest(50000);
    }
}
