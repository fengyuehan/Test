package com.example.roomdemo.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(onDelete = CASCADE,onUpdate = CASCADE,entity = Person.class,parentColumns = "uid",childColumns = "father_id"),indices = {@Index(value = "father_id")})
public class Clothes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String color;

    private int father_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFather_id() {
        return father_id;
    }

    public void setFather_id(int father_id) {
        this.father_id = father_id;
    }
}
