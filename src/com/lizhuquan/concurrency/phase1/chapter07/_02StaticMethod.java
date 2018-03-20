package com.lizhuquan.concurrency.phase1.chapter07;

public class _02StaticMethod {

    public synchronized static void m1() {
        try {
            System.out.println("m1 block lock by " + Thread.currentThread().getName());
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m2() {
        try {
            System.out.println("m1 execute by " + Thread.currentThread().getName());
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void m3() {
        try {
            System.out.println("m1 execute by " + Thread.currentThread().getName());
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
