package com.jeysin.concurrency;

import java.util.concurrent.BlockingQueue;

/**
 * @Author: Jeysin
 * @Date: 2019/4/18 16:09
 * @Desc:
 */

public class PoolThread extends Thread {

    private BlockingQueue<Runnable> taskQueue = null;

    private volatile boolean isStopped = false;

    public PoolThread(BlockingQueue<Runnable> queue){
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        while(!isStopped){
            try{
                Runnable runnable = taskQueue.take();
                runnable.run();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void toStop(){
        isStopped = true;
        this.interrupt();
    }
}
