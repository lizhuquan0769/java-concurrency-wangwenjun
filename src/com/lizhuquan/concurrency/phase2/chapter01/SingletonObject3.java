package com.lizhuquan.concurrency.phase2.chapter01;

/**
 * 懒加载模式 + synchronized
 */
public class SingletonObject3 {

    private static SingletonObject3 instance;

    private SingletonObject3() {

    }

    // 线程安全， 但每次读操作都需要锁， 串行化影响性能
    public synchronized static SingletonObject3 getInstance() {

        if (null == instance) {
            instance = new SingletonObject3();
        }
        return SingletonObject3.instance;
    }
}
