package com.jeysin.concurrency;

/**
 * @Author: Jeysin
 * @Date: 2019/4/16 12:07
 * @Desc: 非公平锁的实现，可能存在线程饿死现象
 */

public class UnfairLock {

    private volatile boolean isLocked = false;

    private Thread lockingThread = null;

    public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }
        isLocked = false;
        lockingThread = null;
        notify();
    }
}
