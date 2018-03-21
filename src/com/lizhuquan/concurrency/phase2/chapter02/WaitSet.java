package com.lizhuquan.concurrency.phase2.chapter02;

import java.util.Optional;
import java.util.stream.IntStream;

public class WaitSet {

    private static final Object LOCK = new Object();

    private static void work() {
        synchronized (LOCK) {
            System.out.println("Begin....");

            try {
                System.out.println("Thread will coming");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread will out.");
        }
    }

    /**
     * 1.所有的对象都会有一个wait set,用来存放调用了该对象wait方法之后进入block状态线程
     * 2.线程被notify之后，不一定立即得到执行
     * 3.线程从wait set中被唤醒顺序不一定是FIFO.
     * 4.线程被唤醒后，必须重新获取锁
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
//        new Thread() {
//            @Override
//            public void run() {
//                work();
//            }
//        }.start();
//
//        Thread.sleep(1000);
//        synchronized (LOCK) {
//            LOCK.notify();
//        }

        IntStream.rangeClosed(1, 10).forEach(i ->
                new Thread(String.valueOf(i)) {
                    @Override
                    public void run() {
                        synchronized (LOCK) {
                            try {
                                Optional.of(Thread.currentThread().getName() + " will come to wait set.").ifPresent(System.out::println);
                                LOCK.wait();
                                Optional.of(Thread.currentThread().getName() + " will leave to wait set.").ifPresent(System.out::println);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start()
        );

        Thread.sleep(3000);

        IntStream.rangeClosed(1, 10).forEach(i ->
                {
                    synchronized (LOCK) {
                        LOCK.notify();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }
}