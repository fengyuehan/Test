package com.example.alib;

import com.example.ainterfacelib.CInterface;
import com.google.auto.service.AutoService;

@AutoService(CInterface.class)
public class C1Impl implements CInterface {
    @Override
    public String getName() {
        return "C1Impl";
    }
}
