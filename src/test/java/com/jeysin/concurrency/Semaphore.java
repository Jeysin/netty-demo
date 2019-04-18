package com.jeysin.concurrency;

/**
 * @Author: Jeysin
 * @Date: 2019/4/18 15:16
 * @Desc: 信号量的实现
 */

public class Semaphore {

    private volatile boolean signal = false;

    public synchronized void take(){
        this.signal = true;
        this.notify();
    }

    public synchronized void release() throws InterruptedException{
        while(!this.signal){
            wait();
        }
        this.signal = false;
    }
}
