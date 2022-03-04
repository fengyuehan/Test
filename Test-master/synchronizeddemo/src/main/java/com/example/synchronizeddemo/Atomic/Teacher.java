package com.example.synchronizeddemo.Atomic;

/**
 * author : zhangzf
 * date   : 2020/12/22
 * desc   :
 */
public class Teacher {
    /**
     * 教师名称
     */
    public volatile String name;

    /**
     * 学生投票数
     */
    public volatile int ticketNum;

    public Teacher(String name, int ticketNum) {
        this.name = name;
        this.ticketNum = ticketNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }
}
