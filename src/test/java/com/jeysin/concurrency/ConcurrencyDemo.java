package com.jeysin.concurrency;

import com.jeysin.io.pojo.Student;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Jeysin
 * @Date: 2019/4/8 18:06
 * @Desc:
 */

public class ConcurrencyDemo {

    private static long count = 0;

    private synchronized static void addCount(){
        ++count;
    }

    private synchronized static void addCount2(){
        ++count;
    }

    private static Lock lock = new ReentrantLock();

    private static void addCount3(){
        lock.lock();
        ++count;
        lock.unlock();
    }

    private static void CreateThreadTest(){
        System.out.println(Thread.currentThread().getName());
        for(int i=0; i<10; ++i){
            Thread thread = new Thread(String.valueOf(i)){
                @Override
                public void run() {
                    for(int i=0; i<10000; i++){
                        addCount3();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            };
            thread.start();
            try {
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(count);
    }

    public static class MyRunnable implements Runnable{
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadLocal.set((int)(Math.random() * 100));
            try{
                Thread.sleep(2 * 1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }
    }

    public static void ThreadLocalTest(){
        MyRunnable myRunnable = new MyRunnable();
        Thread thread1 = new Thread(myRunnable, "thread1");
        Thread thread2 = new Thread(myRunnable, "thread2");

        thread1.start();
        thread2.start();


        try {
            thread1.join();
            thread2.join();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        CreateThreadTest();
        //ThreadLocalTest();
    }
}
