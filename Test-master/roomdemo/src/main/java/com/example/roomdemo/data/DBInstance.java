package com.example.roomdemo.data;

import androidx.room.Room;

import com.example.roomdemo.MyApp;

public class DBInstance {
    private static final String DB_NAME = "room_test";
    public static AppDataBase appDataBase;

    public static AppDataBase getInstance(){
        if (appDataBase == null){
            synchronized (DBInstance.class){
                if (appDataBase == null){
                    appDataBase = Room.databaseBuilder(MyApp.getMyApp(),AppDataBase.class,DB_NAME)
                            //一般不打开，这句是指在主线程进行查询，因为数据操作是耗时操作
                            .allowMainThreadQueries()
                            //.addMigrations(AppDataBase.MIGRATION_1_2)
                            .build();
                }
            }
        }
        return appDataBase;
    }
}
