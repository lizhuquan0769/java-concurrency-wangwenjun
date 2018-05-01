package com.lizhuquan.concurrency.phase3.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PhaserExample {

    private final static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(100);

        IntStream.rangeClosed(1,5).boxed().map(i -> phaser).forEach(PhaserThread::new);
        phaser.arriveAndAwaitAdvance();
        System.out.println("all works is done in phase");
    }

    static class PhaserThread extends Thread {

        private Phaser phaser;

        public PhaserThread(Phaser phaser) {
            this.phaser = phaser;
            start();
        }

        @Override
        public void run() {
            System.out.println(getName() + " is working in phase");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(getName() + " work is done in phase");
            phaser.arriveAndAwaitAdvance();
            System.out.println(getName() + " wait finish");
        }
    }
}
