package com.lizhuquan.concurrency.phase3.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorExample {

    private final static AtomicInteger tCounter = new AtomicInteger();

    /**
     *
         int corePoolSize,
         int maximumPoolSize,
         long keepAliveTime,
         TimeUnit unit,
         BlockingQueue<Runnable> workQueue,
         RejectedExecutionHandler handler
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        // build thread-pool
        ThreadPoolExecutor threadPoolExecutor = buildThreadPoolExecutor();
        System.out.println("thread-pool-executor was built");

        // submit tasks
        threadPoolExecutor.submit(() -> sleepSeconds(50));
        threadPoolExecutor.submit(() -> sleepSeconds(50));
        threadPoolExecutor.submit(() -> sleepSeconds(10));
//        threadPoolExecutor.submit(() -> sleepSeconds(10)); // active-count greater than max-size plus queue-size, will trigger the reject policy

        // monitor the thread-pool-args
        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            int activeCountTemp = threadPoolExecutor.getActiveCount();
            int queueSizeTemp = threadPoolExecutor.getQueue().size();
            if (activeCount != activeCountTemp || queueSize != queueSizeTemp) {
                System.out.println("========================华丽的分割线===========================");
                System.out.println("ActiveCount: " + activeCountTemp);
                System.out.println("QueueSize: " + queueSizeTemp);
                System.out.println("CorePoolSize: " + threadPoolExecutor.getCorePoolSize());
                System.out.println("MaxPoolSize: " + threadPoolExecutor.getMaximumPoolSize());
                activeCount = activeCountTemp;
                queueSize = queueSizeTemp;
                if (activeCount == 0 && queueSize == 0) {
                    System.out.println("all task is over...");
                }
            }
        }
    }

    private static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ThreadPoolExecutor buildThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);
                        t.setName("thread-pool-executor-" + tCounter.getAndIncrement());
                        return t;
                    }
                }, new ThreadPoolExecutor.AbortPolicy());
        return threadPoolExecutor;
    }
}
