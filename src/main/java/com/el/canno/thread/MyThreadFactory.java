package com.el.canno.thread;

import java.util.concurrent.ThreadFactory;

/**
 * User Canno
 * Date 2018/3/28
 * Time 10:12
 */
public class MyThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread();
    }
}
