package com.lizhuquan.concurrency.phase2.chapter01_单例模式;

/**
 * Initialization on Demand Holder模式
 */
public class SingletonObject6 {

    private SingletonObject6() {

    }

    public static SingletonObject6 getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 在初始化这个内部类的时候，JLS(Java Language Sepcification)会保证这个类的线程安全(the class initialization phase is guaranteed by the JLS to be serial)
     */
    private static class SingletonHolder {
        private final static SingletonObject6 instance = new SingletonObject6();
    }
}
