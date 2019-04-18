package com.jeysin.concurrency;

/**
 * @Author: Jeysin
 * @Date: 2019/4/18 15:21
 * @Desc: 有上限的信号量的实现
 */

public class BoundedSemaphore {

    private volatile int signal = 0;

    private volatile int bound = 0;

    public BoundedSemaphore(int bound){
        this.bound = bound;
    }

    public synchronized void take() throws InterruptedException{
        while(this.signal == this.bound){
            wait();
        }
        ++signal;
        notify();
    }

    public synchronized void release() throws InterruptedException{
        while(signal == 0){
            wait();
        }
        --signal;
        notify();
    }
}
