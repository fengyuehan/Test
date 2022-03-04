package com.example.dell.myapplication;

import android.util.Log;

public class StringDemo {
    public static void main(String[] args){
        String a = "123";
        changeChar(a);
        System.out.println(a);
    }

    private static void changeChar(String a) {
        a = "abc";
    }
}
