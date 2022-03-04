package com.example.reflect;

import android.util.Log;

import androidx.annotation.NonNull;

public class Apple extends Fruit {
    String color;
    public int size;
    private int price;

    public Apple(){
        Log.e("zzf","Apple的无参构造");
        //System.out.println("Apple的无参构造");
    }

    private Apple(String color, int size) {
        this.color = color;
        this.size = size;
        this.price = 10;
        Log.e("zzf","Apple的有参构造——两个参数");
        //System.out.println("Apple的有参构造——两个参数");
    }

    public Apple(String color, int size, int price) {
        this.color = color;
        this.size = size;
        this.price = price;
        Log.e("zzf","Apple的有参构造——三个参数");
        //System.out.println("Apple的有参构造——三个参数");
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                ", size=" + size +
                ", price=" + price +
                '}';
    }
}
