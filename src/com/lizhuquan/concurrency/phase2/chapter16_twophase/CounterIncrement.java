package com.lizhuquan.concurrency.phase2.chapter16_twophase;

import java.util.Random;

public class CounterIncrement extends Thread {

    private volatile boolean terminated = false;

    private int counter = 0;

    private Random random = new Random();

    @Override
    public void run() {
        try {
            while (!terminated) {
                System.out.println(Thread.currentThread().getName() + " " + counter++);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            System.err.println(">>>>> counter increment be interrupted...");
        } finally {
            clean();
        }
    }

    private void clean() {
        System.err.println(">>>>> all resources will be cleaned");

    }

    public void close() {
        this.terminated = true;
        this.interrupt();
    }
}
