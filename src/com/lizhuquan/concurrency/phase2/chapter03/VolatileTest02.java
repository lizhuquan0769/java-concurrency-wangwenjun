package com.lizhuquan.concurrency.phase2.chapter03;

/**
 * 测试volatile的写写可见性
 * Created by lizhuquan on 2018/3/21.
 */
public class VolatileTest02 {

    private static int SHARE_VAL = 0;

    private final static int MAX_VAL = 500;

    public static void main(String[] args) {
        new Thread(() -> {
            while (SHARE_VAL < MAX_VAL) {
                System.out.printf(Thread.currentThread().getName() + ": share value add to %d\n", ++SHARE_VAL);
            }
        }, "ADDER-1").start();

        new Thread(() -> {
            while (SHARE_VAL < MAX_VAL) {
                System.out.printf(Thread.currentThread().getName() + ": share value add to %d\n", ++SHARE_VAL);
            }
        }, "ADDER-2").start();
    }
}
