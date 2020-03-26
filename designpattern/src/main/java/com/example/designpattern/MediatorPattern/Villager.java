package com.example.designpattern.MediatorPattern;

public abstract class Villager {
    protected PostOffice postOffice;
    protected String address;

    public Villager(PostOffice postOffice,String address){
        this.postOffice = postOffice;
        this.address = address;
    }

    public void receiveLetter(String letter){
        System.out.println(letter);
    }

    public void sendLetter(String letter,String receiver){
        postOffice.deliverLetters(letter,receiver);
    }

    public String getAddress(){
        return address;
    }
}
