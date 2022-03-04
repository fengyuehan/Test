package com.example.shoppingcart;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ShopCar {
    @Id(autoincrement = true)
    private Long id;
    //数量
    public int num;
    //名称
    public String name;
    //金额
    public double price;
    //库存
    public String stockCount;
    @Generated(hash = 2146937109)
    public ShopCar(Long id, int num, String name, double price, String stockCount) {
        this.id = id;
        this.num = num;
        this.name = name;
        this.price = price;
        this.stockCount = stockCount;
    }
    @Generated(hash = 1637372148)
    public ShopCar() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getStockCount() {
        return this.stockCount;
    }
    public void setStockCount(String stockCount) {
        this.stockCount = stockCount;
    }

}
