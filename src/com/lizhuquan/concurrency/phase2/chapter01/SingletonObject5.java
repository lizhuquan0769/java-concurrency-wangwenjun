package com.lizhuquan.concurrency.phase2.chapter01;

/**
 * 懒加载模式 + double check + volatile
 */
public class SingletonObject5 {

    // volatile保证可见性
    private static volatile SingletonObject5 instance;

    private SingletonObject5() {

    }

    public static SingletonObject5 getInstance() {

        if (null == instance) {
            synchronized (SingletonObject5.class) {
                if (null == instance) {
                    instance = new SingletonObject5();
                }
            }
        }
        return SingletonObject5.instance;
    }
}
