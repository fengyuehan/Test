package com.example.designpattern.FactoryPattern;

import android.text.TextUtils;

public class Factory {
    public IOHandler getHandler(String str){
        if(TextUtils.isEmpty(str)){
            return null;
        }
        if(str.equals("DBHandler")){
            return new DBHandler();
        }else if (str.equals("FileHandler")){
            return new FileHandler();
        }
        return null;
    }
}
