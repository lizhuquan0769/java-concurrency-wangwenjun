package com.lizhuquan.concurrency.phase2.chapter01_单例模式;

/**
 * 饿汉模式
 */
public class SingletonObject1 {

    /**
     * can't lazy load.
     */
    private static final SingletonObject1 instance = new SingletonObject1();

    private SingletonObject1() {

    }

    public static SingletonObject1 getInstance() {

        return instance;
    }
}
