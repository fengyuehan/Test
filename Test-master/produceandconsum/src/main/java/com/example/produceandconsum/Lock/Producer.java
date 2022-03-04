package com.example.produceandconsum.Lock;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author user
 * @Date 2019/11/28
 * @Version 1.0
 */
public abstract class Producer<T> implements Runnable {
    private BlockedQueue<T> queue;

    public Producer(BlockedQueue<T> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            T[] tasks = generateTask();
            if (null != tasks && tasks.length > 0){
                for (T task:tasks){
                    if (null != task){
                        this.queue.enter(task);
                    }
                }
            }
        }
    }

    protected abstract T[] generateTask();
}
