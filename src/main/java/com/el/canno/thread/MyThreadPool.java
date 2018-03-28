package com.el.canno.thread;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * User Canno
 * Date 2018/3/28
 * Time 10:03
 */
public class MyThreadPool {
    private final ThreadPoolExecutor executor;
    private final ThreadPool threadPool = ThreadPool.wrap(new MyExecutorService(), 10);

    public MyThreadPool() {
        executor = new ThreadPoolExecutor(100,100,10000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(20),new MyThreadFactory());
    }
}
