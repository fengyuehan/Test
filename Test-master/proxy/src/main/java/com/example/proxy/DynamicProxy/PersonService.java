package com.example.proxy.DynamicProxy;

import android.util.Log;

/**
 * @ClassName PersonService
 * @Description TODO
 * @Author user
 * @Date 2019/12/10
 * @Version 1.0
 */
public class PersonService implements IPersonService {
    @Override
    public void addPerson() {
        Log.e("zzf","添加人物");
    }

    @Override
    public void deletePerson() {
        Log.e("zzf","删除人物");
    }
}
