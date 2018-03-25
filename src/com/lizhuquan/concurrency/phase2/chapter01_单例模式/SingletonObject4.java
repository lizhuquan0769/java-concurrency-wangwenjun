package com.lizhuquan.concurrency.phase2.chapter01_单例模式;

/**
 * 懒加载模式 + double check
 */
public class SingletonObject4 {

    private static volatile SingletonObject4 instance;

    private Object obj;

    private SingletonObject4() {
        this.obj = new Object();
    }

    public static SingletonObject4 getInstance() {

        // 这一步非空判断解决性能问题
        if (null == instance) {
            // 这一步synchronized解决并发问题
            synchronized (SingletonObject4.class) {
                if (null == instance) {
                    /**
                     * 问：在double check的单例模式中，instance为什么需要使用volatile变量声明呢？
                     *
                     * 原因：对象的实例化过程中，内存地址的开辟指令也是允许重排序的，
                     *  例如new SingletonObject4()需要开个两个对象的内存地址，SingletonObject4对象需要开辟， SingletonObject4的成员变量obj对象也需要开辟
                     *  正常的情况下，SingletonObject4对象的开辟应该要在所有成员变量的开辟之后，如果发生重排序的话，SingletonObject4对象可能先开辟了， 还没等obj对象的开辟就释放锁返回了
                     *  然后第二个线程抢到锁进来的时候，判断instance对象非空，获取instance对象，然后对obj进行操作，次数obj并没有开辟，所以会造成空指针异常
                     *
                     * 结论：所以这里需要为instance加上volatile修饰，禁止对开辟顺序进行重排序，从而保证SingletonObject4等obj开辟完之后再进行开辟，避免了空指针异常
                     */
                    instance = new SingletonObject4();
                }
            }
        }
        return SingletonObject4.instance;
    }
}
