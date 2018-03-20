package com.lizhuquan.concurrency.phase2.chapter01;

import java.util.stream.IntStream;

/**
 * 懒加载模式
 */
public class SingletonObject2 {

    private static SingletonObject2 instance;

    private SingletonObject2() {

    }

    public static SingletonObject2 getInstance() {

        if (null == instance) {
            // 这一步有并发问题， 但线程不安全
            instance = new SingletonObject2();
        }
        return SingletonObject2.instance;
    }
}
