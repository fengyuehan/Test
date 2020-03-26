package com.example.designpattern.ObserverPattern;

public class NBAObserver extends Observer {
    private String name;
    private Subject subject;

    public NBAObserver(String name, Subject subject) {
        super(name, subject);
        this.name = name;
        this.subject = subject;
    }

    @Override
    public void updata() {
        System.out.println(subject.getAction() + "\n" + name + "关闭股票行情，继续工作");
    }
}
