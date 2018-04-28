package com.el.canno.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Canno
 * @since 2018/4/28 15:50
 */
public class TaskExecutors {
    /**
     *
     * @param cacheSize BlockingQueue size
     * @param producerSize Producer thread size
     * @param consumerSize {@link Consumer} Consumer threadSize
     * @param producerExeTime {@link Producer} Producer execute time
     * @param consumerExeTime Consumer execute time
     * @throws InterruptedException throw Exception
     */
    public static void execute(int cacheSize, int producerSize, int consumerSize, long producerExeTime, long consumerExeTime) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(cacheSize);

        // new a threadPool
        ExecutorService service = Executors.newCachedThreadPool();
        List<Producer> producers = new ArrayList<>();
        for (int i = 0; i < producerSize; i++) {
            Producer producer = new Producer(queue);
            producers.add(producer);
        }

        List<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < consumerSize; i++) {
            Consumer consumer = new Consumer(queue);
            consumers.add(consumer);
        }
        producers.forEach(service::execute);
        consumers.forEach(service::execute);

        if (producerExeTime < consumerExeTime) {
            Thread.sleep(producerExeTime);
            producers.forEach(Producer::stop);
            Thread.sleep(consumerExeTime - producerExeTime);
            consumers.forEach(Consumer::stop);
        } else {
            Thread.sleep(consumerExeTime);
            consumers.forEach(Consumer::stop);
            Thread.sleep(producerExeTime - consumerExeTime);
            producers.forEach(Producer::stop);
        }

        Thread.sleep(1000);
        // 退出Executor
        service.shutdown();
    }
}
