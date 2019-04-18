package com.jeysin.concurrency;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Jeysin
 * @Date: 2019/4/16 22:33
 * @Desc: 可重入读写锁的实现
 */

public class ReentrantReadWriteLock {

    private Map<Thread, Integer> readingThreadsMap = new HashMap<Thread, Integer>();

    private volatile int writers = 0;

    private volatile int writeRequests = 0;

    private volatile Thread writingThread = null;

    public synchronized void lockRead() throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(!canGrantReadAccess(callingThread)){
            wait();
        }
        readingThreadsMap.put(callingThread,getAccessCount(callingThread) + 1);
    }

    public synchronized void unlockRead(){
        Thread callingThread = Thread.currentThread();
        int count = getAccessCount(callingThread);
        if(count == 1){
            readingThreadsMap.remove(callingThread);
        }else {
            readingThreadsMap.put(callingThread, count-1);
        }
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException{
        ++writeRequests;
        Thread callingThread = Thread.currentThread();
        while(!canGrantWriteAccess(callingThread)){
            wait();
        }
        --writeRequests;
        ++writers;
        writingThread = callingThread;
    }

    public synchronized void unlockWrite(){
        --writers;
        if(writers == 0){
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantWriteAccess(Thread callingThread){
        if(readingThreadsMap.size() > 0){
            return false;
        }
        if(writers > 0 && writingThread != callingThread){
            return false;
        }
        return true;
    }

    private boolean canGrantReadAccess(Thread callingThread){
        if(writers > 0){
            return false;
        }
        if(readingThreadsMap.get(callingThread) != null){
            return true;
        }
        if(writeRequests > 0){
            return false;
        }
        return true;
    }

    private Integer getAccessCount(Thread callingThread){
        Integer count = readingThreadsMap.get(callingThread);
        if(count == null){
            return 0;
        }
        return count;
    }
}
