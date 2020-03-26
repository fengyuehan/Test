package com.example.designpattern.MediatorPattern;

import java.util.HashMap;

public class PostOfficeImpl implements PostOffice {
    private HashMap<String,Villager> map = new HashMap<>();

    @Override
    public void deliverLetters(String letters, String receiver) {
        System.out.println("=>收信：邮局收到要寄的信");
        Villager villager = (Villager) map.get(receiver);
        System.out.println("=>送信：拿出地址本查询收信人地址是：" + villager.getAddress() + "，送信");
        System.out.println("=>收信人看信：");
        villager.receiveLetter(letters);
    }

    @Override
    public void addPeople(Villager villager) {
        map.put(villager.getClass().getSimpleName(),villager);
    }
}
