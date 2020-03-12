package com.example.roomdemo.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Dog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}
