package com.lizhuquan.concurrency.phase2.chapter16_twophase;

public class TwoPhaseTest {

    public static void main(String[] args) throws InterruptedException {
        CounterIncrement counterIncrement = new CounterIncrement();
        counterIncrement.start();

        Thread.sleep(10_000);

        counterIncrement.close();
    }
}
