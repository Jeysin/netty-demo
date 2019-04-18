package com.jeysin.concurrency;

/**
 * @Author: Jeysin
 * @Date: 2019/4/16 22:01
 * @Desc: 不可重入的读写锁实现
 */

public class ReadWriteLock {
    private volatile int readers = 0;
    private volatile int writers = 0;
    private volatile int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException{
        while(writers > 0 || writeRequests > 0){
            this.wait();
        }
        ++readers;
    }

    public synchronized void unlockRead(){
        --readers;
        this.notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException{
        ++writeRequests;
        while(readers > 0 || writers > 0){
            wait();
        }
        --writeRequests;
        ++writers;
    }

    public synchronized void unlockWrite(){
        --writers;
        notifyAll();
    }
}
