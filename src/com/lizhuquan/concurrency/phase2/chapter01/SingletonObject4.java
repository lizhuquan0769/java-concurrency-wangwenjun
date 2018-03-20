package com.lizhuquan.concurrency.phase2.chapter01;

/**
 * 懒加载模式 + double check
 */
public class SingletonObject4 {

    private static SingletonObject4 instance;

    private SingletonObject4() {

    }

    public static SingletonObject4 getInstance() {

        // 这一步非空判断解决性能问题
        if (null == instance) {
            // 这一步synchronized解决并发问题
            synchronized (SingletonObject4.class) {
                if (null == instance) {
                    // 但是，这里可能存在重排序而导致另外的线程发生空指针异常
                    instance = new SingletonObject4();
                }
            }
        }
        return SingletonObject4.instance;
    }
}
