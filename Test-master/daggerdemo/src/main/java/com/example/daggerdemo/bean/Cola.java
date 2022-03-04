package com.example.daggerdemo.bean;

import javax.inject.Inject;

public class Cola {
    public Cola(){
    }

    //没有加model
    /*@Inject
    public Cola(){
    }*/
    public String getName(){
        return "百事可乐";
    }
}
