package com.example.jetpack.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "student")
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    public String name;

    @ColumnInfo(name = "age",typeAffinity = ColumnInfo.TEXT)
    public String age;

    /**
     * Room会使用这个构造器来存储数据，当从表中得到Student的对象时，就会使用这个构造函数
     * @param id
     * @param name
     * @param age
     */
    public Student(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    /**
     * 由于Room只能识别一个构造器，如果需要多个构造器，用Ignore标签，
     * 同样，@Ignore标签还可用于字段，使用@Ignore标签标记过的字段，Room不会持久化该字段的数据
     * @param name
     * @param age
     */
    @Ignore
    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
