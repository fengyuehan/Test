package com.example.designpattern.proxyPattern;

public class Self implements Collectable {
    private Friend friend;

    public Self(Friend friend){
        this.friend = friend;
    }

    @Override
    public void collectPack() {
        friend.collectPack();
    }
}
