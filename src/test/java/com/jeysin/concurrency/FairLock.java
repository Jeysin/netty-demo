package com.jeysin.concurrency;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jeysin
 * @Date: 2019/4/16 12:16
 * @Desc: 公平锁的实现，不会存在线程饿死现象。
 * 实现原理：每个线程在不同的对象上调用wait方法，Lock类可以决定调用哪个对象的notify方法，所以可以做到唤醒特定的线程
 */

public class FairLock {

    private volatile boolean isLocked = false;

    private Thread lockingThread = null;

    private List<QueueObject> waitingThreads = new ArrayList<QueueObject>();

    public void lock() throws InterruptedException{
        QueueObject queueObject = new QueueObject();
        boolean isLockedForThisThread = true;
        synchronized (this){
            waitingThreads.add(queueObject);
        }
        while(isLockedForThisThread){
            synchronized (this) {
                isLockedForThisThread = isLocked || waitingThreads.get(0) != queueObject;
                if (!isLockedForThisThread) {
                    isLocked = true;
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread();
                    return;
                }
            }
            try{
                queueObject.doWait();
            }catch (InterruptedException e){
                synchronized (this){
                    waitingThreads.remove(queueObject);
                    throw e;
                }
            }
        }
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }
        isLocked = false;
        lockingThread = null;
        if(waitingThreads.size() > 0){
            waitingThreads.get(0).doNotify();
        }
    }
}
