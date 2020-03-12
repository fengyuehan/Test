package com.example.roomdemo.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {
    //查询所有数据
    @Query("Select * from person")
    List<Person> getAll();

    //删除所有数据
    @Query("DELETE FROM person")
    void deleteAll();

    //一次插入单条数据或多条数据
    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert
    void insert(Person... persons);


    //一次删除单挑数据和多条数据
    @Delete
    void delete(Person... persons);

    //一次更新单挑数据或多条
    @Update
    void update(Person... persons);

    //根据字段去查询数据
    //@Query("SELECT * FROM person WHERE uid= :uid")//这种是有别名
    @Query("SELECT * FROM person WHERE uid = :uid")
    Person getPersonByUid(int uid);

    //多个条件查找
    @Query("SELECT * FROM person WHERE name = :name AND age = :age")
    Person getPersonByAge(String name,int age);



}
