package com.lizhuquan.concurrency.phase2.chapter01;

/**
 * holder模式
 */
public class SingletonObject6 {

    private SingletonObject6() {

    }

    /**
     * static只会在jvm中初始化一次， 第一次调用的时候getInstance的时候才会new SingletonObject6();
     */
    public static SingletonObject6 getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private final static SingletonObject6 instance = new SingletonObject6();
    }
}
