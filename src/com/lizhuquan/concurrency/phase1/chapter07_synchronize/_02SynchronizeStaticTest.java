package com.lizhuquan.concurrency.phase1.chapter07_synchronize;

public class _02SynchronizeStaticTest {

    static {
        synchronized (_02SynchronizeStaticTest.class) {
            try {
                System.out.println("static block lock by " + Thread.currentThread().getName());
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread("t1") {
            @Override
            public void run() {
                _02StaticMethod.m1();
            }
        }.start();

        new Thread("t2") {
            @Override
            public void run() {
                _02StaticMethod.m2();
            }
        }.start();

        new Thread("t3") {
            @Override
            public void run() {
                _02StaticMethod.m3();
            }
        }.start();
    }
}
