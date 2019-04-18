package com.jeysin.concurrency;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Jeysin
 * @Date: 2019/4/18 15:43
 * @Desc: 阻塞队列的实现
 */

public class BlockQueue {

    private List queue = new LinkedList();

    private volatile int limit = 10;

    public BlockQueue(int limit){
        this.limit = limit;
    }

    public synchronized void enqueue(Object object) throws InterruptedException{
        while(this.queue.size() > limit){
            wait();
        }
        if(this.queue.size() == 1){
            notifyAll();
        }
        queue.add(object);
    }

    public synchronized Object dequeue() throws InterruptedException{
        while(this.queue.size() == 0){
            wait();
        }
        if(this.queue.size() == limit){
            notifyAll();
        }
        return this.queue.remove(0);
    }
}
