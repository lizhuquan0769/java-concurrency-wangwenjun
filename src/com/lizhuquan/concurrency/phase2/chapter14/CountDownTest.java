package com.lizhuquan.concurrency.phase2.chapter14;

import java.util.Random;
import java.util.stream.IntStream;

public class CountDownTest {

    public static void main(String[] args) throws InterruptedException {

        CountDown countDown = new CountDown(5);

        Random random = new Random();

        IntStream.rangeClosed(1,5).forEach(i -> {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " working...");
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDown.down();
            },"T" + i).start();
        });

        countDown.await();
        System.out.println("all work done");
    }
}
