package com.lizhuquan.concurrency.phase2.chapter03_volatile;

/**
 * 测试volatile的写写可见性
 * Created by lizhuquan on 2018/3/21.
 */
public class VolatileTest02 {

    private volatile static int SHARE_VAL = 0;

    private final static int MAX_VAL = 500;

    public static void main(String[] args) {
        new Thread(() -> {
            while (SHARE_VAL < MAX_VAL) {
                synchronized (VolatileTest02.class) {
                    System.out.printf(Thread.currentThread().getName() + ": share value add to %d\n", ++SHARE_VAL);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-1").start();

        new Thread(() -> {
            while (SHARE_VAL < MAX_VAL) {
                synchronized (VolatileTest02.class) {
                    System.out.printf(Thread.currentThread().getName() + ": share value add to %d\n", ++SHARE_VAL);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER-2").start();
    }
}
