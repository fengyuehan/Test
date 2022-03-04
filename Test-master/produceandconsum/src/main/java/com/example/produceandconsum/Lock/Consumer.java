package com.example.produceandconsum.Lock;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author user
 * @Date 2019/11/28
 * @Version 1.0
 */
public abstract class Consumer<T> implements Runnable {
    private BlockedQueue<T> queue;

    public Consumer(BlockedQueue<T> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            T task = queue.out();
            exec(task);
        }
    }

    protected abstract void exec(T task);
}
