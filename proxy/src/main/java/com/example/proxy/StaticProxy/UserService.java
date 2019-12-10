package com.example.proxy.StaticProxy;

import android.util.Log;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author user
 * @Date 2019/12/10
 * @Version 1.0
 */
public class UserService implements IUserService {
    @Override
    public void addUser() {
        Log.e("zzf","添加员工");
    }

    @Override
    public void deleteUser() {
        Log.e("zzf","删除员工");
    }
}
