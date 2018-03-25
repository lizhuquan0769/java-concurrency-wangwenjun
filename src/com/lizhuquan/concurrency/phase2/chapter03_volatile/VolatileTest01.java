package com.lizhuquan.concurrency.phase2.chapter03_volatile;

/**
 * 测试volatile的读写可见性
 * Created by lizhuquan on 2018/3/21.
 */
public class VolatileTest01 {

    // 这里加或者不加volatile会有不同的现象
    private static int SHARE_VAL = 0;

    private final static int MAX_VAL = 50;

    public static void main(String[] args) {

        /**
         * 如果SHARE_VAL没有volatile修饰,  java虚拟机会判断如果SHARE_VAL都是读操作, 则会做性能优化
         * 认为没必要去Main Cache里面拿数据, 只从CPU Cache拿数据就可以了, 所以导致多线程共享变量的可见性问题
         *
         * 如果要感知线程内只读变量的可见性问题, 需要给该变量设置volatile关键字来解决
         */
        new Thread(() -> {
            int localVal = SHARE_VAL;
            while (localVal < MAX_VAL) {
                if (localVal != SHARE_VAL) {
                    System.out.printf(Thread.currentThread().getName() + ": share value was updated to %d\n", SHARE_VAL);
                    localVal = SHARE_VAL;
                }
            }
        }, "READER").start();

        new Thread(() -> {
            int localVal = SHARE_VAL;
            while (localVal < MAX_VAL) {
                System.out.printf(Thread.currentThread().getName() + ": add share value to %d\n", ++localVal);
                SHARE_VAL = localVal;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADDER").start();
    }
}
