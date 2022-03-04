package com.example.designpattern.IteratorPattern;

/**
 * author : zhangzf
 * date   : 2021/2/3
 * desc   :
 */
public abstract class Leader {
    protected Leader nextHandler;

    public final void handleReqest(int money){
        if (money < limit()){
            handle(money);
        }else {
            if (nextHandler != null){
                nextHandler.handleReqest(money);
            }
        }
    }

    protected abstract void handle(int money);

    protected abstract int limit();
}
