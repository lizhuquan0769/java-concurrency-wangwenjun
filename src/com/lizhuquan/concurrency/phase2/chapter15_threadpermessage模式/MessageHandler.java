package com.lizhuquan.concurrency.phase2.chapter15_threadpermessage模式;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread-Pre-Message Desgin Pattern
 */
public class MessageHandler {

    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private Random random = new Random();

    public void handle(Message message) {
        threadPool.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " execute message " + message.getValue());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        threadPool.shutdown();
    }
}
