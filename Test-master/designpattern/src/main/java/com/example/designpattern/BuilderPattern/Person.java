package com.example.designpattern.BuilderPattern;

public class Person {
    private String name;
    private String gender;
    private int age;
    private String shoes;
    private String clothes;
    private String money;
    private String car;
    private String house;

    private Person(Builder builder){
        this.name = builder.name;
        this.age = builder.age;
        this.car = builder.car;
        this.gender = builder.gender;
        this.clothes = builder.clothes;
        this.house = builder.house;
        this.money = builder.money;
        this.shoes = builder.shoes;
    }

    public static class Builder{
        private String name;
        private String gender;
        private int age;
        private String shoes;
        private String clothes;
        private String money;
        private String car;
        private String house;

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder gender(String gender){
            this.gender = gender;
            return this;
        }

        public Builder age(int age){
            this.age = age;
            return this;
        }

        public Builder shoes(String shoes){
            this.shoes = shoes;
            return this;
        }

        public Builder clothes(String clothes){
            this.clothes = clothes;
            return this;
        }

        public Builder money(String money){
            this.money = money;
            return this;
        }

        public Builder car(String car){
            this.car = car;
            return this;
        }

        public Builder house(String house){
            this.house = house;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

}
