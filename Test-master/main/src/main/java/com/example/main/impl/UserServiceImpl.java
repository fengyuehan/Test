package com.example.main.impl;


import com.example.main.api.UserInfo;
import com.example.main.api.UserService;

import org.jetbrains.annotations.NotNull;

public class UserServiceImpl implements UserService {

    @NotNull
    @Override
    public UserInfo getUserInfo() {
        return new UserInfo("Tyhj");
    }
}
