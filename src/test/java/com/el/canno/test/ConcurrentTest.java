package com.el.canno.test;

import com.el.canno.concurrent.Consumer;
import com.el.canno.concurrent.Producer;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User Canno
 * Date 2018/3/28
 * Time 9:37
 */
public class ConcurrentTest {

    @Test
    public void test() throws InterruptedException {
            // 声明一个容量为10的缓存队列
            BlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);

            Producer producer1 = new Producer(queue);
            Producer producer2 = new Producer(queue);
            Producer producer3 = new Producer(queue);
            Consumer consumer1 = new Consumer(queue);
            Consumer consumer2 = new Consumer(queue);
            Consumer consumer3 = new Consumer(queue);
            Consumer consumer4 = new Consumer(queue);

            // 借助Executors
            ExecutorService service = Executors.newCachedThreadPool();
            // 启动线程
            service.execute(producer1);
            service.execute(producer2);
            service.execute(producer3);
            service.execute(consumer1);
//            service.execute(consumer2);
//            service.execute(consumer3);
//            service.execute(consumer4);

            // 执行10s
            Thread.sleep(10 * 1000);
            producer1.stop();
            producer2.stop();
            producer3.stop();

            Thread.sleep(2000);
            // 退出Executor
            service.shutdown();
    }
}
