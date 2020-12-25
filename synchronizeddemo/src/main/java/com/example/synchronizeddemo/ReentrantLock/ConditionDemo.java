package com.example.synchronizeddemo.ReentrantLock;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author : zhangzf
 * date   : 2020/12/24
 * desc   :
 */
public class ConditionDemo {

    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    class Consumer implements Runnable{

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (true){
                lock.lock();
                try {
                    while (queue.size() == 0){
                        try {
                            System.out.println("队列空，等待数据");
                            empty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.poll();
                    full.signal();
                    System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    class Producer implements Runnable{

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (true){
                lock.lock();
                try {
                    while(queue.size()== queueSize){
                        try {
                            System.out.println("队列满，等待有空余空间");
                            full.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.offer(1);
                    empty.signal();
                }finally {
                    lock.unlock();
                }
            }

        }
    }
}
