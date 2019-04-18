package com.jeysin.concurrency;

/**
 * @Author: Jeysin
 * @Date: 2019/4/16 12:20
 * @Desc:
 */

public class QueueObject {

    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException{
        while(!isNotified){
            this.wait();
        }
        this.isNotified = false;
    }

    public synchronized void doNotify(){
        this.isNotified = true;
        this.notify();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}
