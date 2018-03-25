package com.lizhuquan.concurrency.phase1.chapter13_自定义线程池;

import java.util.stream.IntStream;

/**
 * Created by lizhuquan on 2018/3/20.
 */
public class SimpleThreadPoolTest {

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool pool = new SimpleThreadPool();

        IntStream.range(0, 200)
                .forEach(i -> {
                    pool.submit(() -> {
                        System.out.println("the runable " + i + " be serviced by " + Thread.currentThread().getName() + " start");
                        try {
                            Thread.sleep(5_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.err.println("the runable " + i + " be serviced by " + Thread.currentThread().getName() + " finish");
                    });
                });

        pool.shutdown();
//        pool.submit(() -> System.out.println("===================="));
    }
}
