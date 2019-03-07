package com.jeysin.JDK.schedule;

import java.util.concurrent.*;

/**
 * @Author: Jeysin
 * @Date: 2019/3/7 23:22
 * @Desc:
 */

public class ScheduleDemo {
    public static void test1(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture future = executor.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("5 seconds later");
                    }
                }, 5, TimeUnit.SECONDS);
        executor.shutdown();
    }

    public static void test2(){
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        ScheduledFuture future = executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("5 seconds later");
                    }
                }, 1, 5, TimeUnit.SECONDS);
    }

    public static void main(String[] args){
        test2();
    }
}
