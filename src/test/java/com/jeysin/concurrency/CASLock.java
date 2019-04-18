package com.jeysin.concurrency;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Jeysin
 * @Date: 2019/4/18 16:47
 * @Desc:
 */

public class CASLock {
    private AtomicBoolean locked = new AtomicBoolean(false);

    public boolean lock(){
        return locked.compareAndSet(false, true);
    }

    public boolean unLock(){
        return locked.compareAndSet(true, false);
    }
}
