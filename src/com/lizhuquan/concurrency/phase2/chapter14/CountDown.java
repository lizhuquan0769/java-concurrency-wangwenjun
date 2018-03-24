package com.lizhuquan.concurrency.phase2.chapter14;

public class CountDown {

    private final int total;

    private int counter = 0;

    public CountDown(int total) {
        this.total = total;
    }

    public void down() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " count down");
            counter++;
            this.notify();
        }
    }

    public void await() throws InterruptedException {
//        synchronized (this) {
            while (counter != total) {
//                this.wait();
            }
//        }
    }
}
