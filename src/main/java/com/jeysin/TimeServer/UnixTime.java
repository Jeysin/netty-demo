package com.jeysin.TimeServer;

import java.util.Date;

/**
 * @Author: Jeysin
 * @Date: 2019/3/19 22:13
 * @Desc:
 */

public class UnixTime {

    private final long value;

    public UnixTime(){
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value){
        this.value = value;
    }

    public long value(){
        return value;
    }

    @Override
    public String toString(){
        return new Date((this.value - 2208988800L) * 1000L).toString();
    }
}
