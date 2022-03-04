package com.example.designpattern.Facade;

public class Facade {
    /**
     * 外观模式
     */
    private SubSystemOne subSystemOne;
    private SubSystemTwo subSystemTwo;
    private SubSystemThree subSystemThree;

    public Facade(){
        subSystemOne = new SubSystemOne();
        subSystemTwo = new SubSystemTwo();
        subSystemThree = new SubSystemThree();
    }

    public void methodA(){
        subSystemOne.MethodeOne();
        subSystemThree.MethodThree();
    }

    public void methodB(){
        subSystemTwo.MethodTwo();
        subSystemThree.MethodThree();
    }
}
