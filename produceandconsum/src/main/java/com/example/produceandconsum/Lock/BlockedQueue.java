package com.example.produceandconsum.Lock;

import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName BlockedQueue
 * @Description TODO
 * @Author user
 * @Date 2019/11/28
 * @Version 1.0
 */
public class BlockedQueue<T> {
    private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();

    private final Condition notEmpty = lock.newCondition();

    private Vector<T> queue = new Vector<T>();

    private int capacity;

    public BlockedQueue(int mCapacity) {
        this.capacity = mCapacity;
    }

    public void enter(T t) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await();
            }
            queue.add(t);
            notEmpty.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T out() {
        lock.lock();
        try {
            try {
                while (queue.size() == 0) {
                    notEmpty.await();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            T t = queue.remove(0);
            notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

}
