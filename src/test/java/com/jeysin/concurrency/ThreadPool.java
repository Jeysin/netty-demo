package com.jeysin.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Jeysin
 * @Date: 2019/4/18 16:07
 * @Desc: 线程池的实现
 */

public class ThreadPool {

    private BlockingQueue<Runnable> taskQueue = null;

    private List<PoolThread> threads = new ArrayList<PoolThread>();

    private volatile boolean isStopped = false;

    public ThreadPool(int threadNums, int maxTaskNums){
        this.taskQueue = new LinkedBlockingQueue<Runnable>(maxTaskNums);
        for(int i=0; i<threadNums; ++i){
            threads.add(new PoolThread(taskQueue));
        }
        for(PoolThread poolThread : threads){
            poolThread.start();
        }
    }

    public synchronized void execute(Runnable task){
        if(this.isStopped){
            throw new IllegalStateException("Thread pool is stopped");
        }
        this.taskQueue.add(task);
    }

    public synchronized void stop(){
        this.isStopped = true;
        for(PoolThread poolThread : threads){
            poolThread.toStop();
        }
    }
}
