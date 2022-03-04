package com.example.roomdemo.objectbox;

import android.app.Application;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxDataManager {
    private static ObjectBoxDataManager objectBoxDataManager;
    public BoxStore boxStore;
    public Box userInfoBox;

    /**
     * 初始化数据库
     * @param application
     */
    public void init(Application application) {
        boxStore = MyObjectBox.builder().androidContext(application).build();
        initUserInfoBox();
    }

    public static synchronized ObjectBoxDataManager getInstance() {
        if (objectBoxDataManager == null) {
            objectBoxDataManager = new ObjectBoxDataManager();
        }
        return objectBoxDataManager;
    }

    private void initUserInfoBox() {
        //对应操作对应表的类
        userInfoBox = boxStore.boxFor(UserInfo.class);
    }

    /**
     * 新增
     * @param userInfo
     */
    public void insertUserInfo(UserInfo userInfo) {
        userInfoBox.removeAll();
        userInfoBox.put(userInfo);
    }

    /**
     * 删除
     */
    public void clearUserInfo() {
        userInfoBox.removeAll();
    }

    /**
     * 改
     * @param userInfo
     */
    public void updateUserInfo(UserInfo userInfo) {
        List<UserInfo> list = userInfoBox.query().build().find();
        if (list!=null&&!list.isEmpty()){
            int pos=-1;
            UserInfo bean;
            for (int i = 0; i <list.size() ; i++) {
                bean=list.get(i);
                if (bean.getId()==userInfo.getId()){
                    pos=i;
                }
            }
            if (pos==-1){
                insertUserInfo(userInfo);
            }else {
                userInfoBox.remove(list.get(pos));
                userInfoBox.put(userInfo);
            }
        }
    }

    /**
     * 查询
     * @return
     */
    public UserInfo queryUserInfo() {
        return (UserInfo) userInfoBox.query().build().findUnique();
    }
}
