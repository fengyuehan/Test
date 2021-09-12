package com.example.a2lib;

import com.example.ainterfacelib.CInterface;
import com.google.auto.service.AutoService;

@AutoService(CInterface.class)
public class C2Impl implements CInterface {
    @Override
    public String getName() {
        return "C2Impl";
    }
}
