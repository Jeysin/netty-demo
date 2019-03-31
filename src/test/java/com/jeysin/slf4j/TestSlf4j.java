package com.jeysin.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Jeysin
 * @Date: 2019/3/25 21:57
 * @Desc:
 */

public class TestSlf4j {
    private static final Logger logger = LoggerFactory.getLogger(TestSlf4j.class);

    public static void main(String[] args){
        int a = 10;
        String b = "jeysin";
        logger.error("error message {}, {}", a, b);
        logger.warn("error message");
        logger.info("error message");
        logger.debug("error message");
        logger.trace("error message");
    }
}
