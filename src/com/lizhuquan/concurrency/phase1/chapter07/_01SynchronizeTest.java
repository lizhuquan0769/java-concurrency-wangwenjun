package com.lizhuquan.concurrency.phase1.chapter07;


/**
 * 进程运行中可以通过
 * 1. 通过jconsole查看进程中线程信息
 * 2. jps 列出所有java进程，jstack打印出堆栈信息
 * 3. javap -c xxx.class 可查看字节码信息
 */
public class _01SynchronizeTest {

    private static Object lock = new Object();

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Thread.sleep(300_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(runnable, "t1");
        Thread t2 = new Thread(runnable, "t2");
        Thread t3 = new Thread(runnable, "t3");

        t1.start();
        t2.start();
        t3.start();
    }
}
