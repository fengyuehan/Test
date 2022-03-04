package com.example.roomdemo.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClothesDao {
    //查询所有数据
    @Query("SELECT * FROM clothes")
    List<Clothes> getAll();

    //删除全部数据
    @Query("DELETE FROM clothes")
    void deleteAll();

    @Insert
    void insert(Clothes... clothes);
}
