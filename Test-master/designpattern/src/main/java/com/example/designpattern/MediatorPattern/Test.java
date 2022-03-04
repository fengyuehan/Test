package com.example.designpattern.MediatorPattern;

public class Test {
    public static void main(String[] args){
        PostOffice postOffice = new PostOfficeImpl();
        Woman woman = new Woman(postOffice,"村东边");
        Man man = new Man(postOffice,"村西边");
        postOffice.addPeople(woman);
        postOffice.addPeople(man);
        woman.receiveLetter("xixi");
        man.receiveLetter("haha");
    }
}
