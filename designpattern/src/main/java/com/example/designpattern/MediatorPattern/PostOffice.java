package com.example.designpattern.MediatorPattern;

public interface PostOffice {
    /**
     * 中介者模式
     */
    /**
     * 送信
     */
    void deliverLetters(String letters, String receiver);

    /**
     * 添加收信人
     */
    void addPeople(Villager villager);
}
