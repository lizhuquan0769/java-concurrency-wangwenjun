package com.lizhuquan.concurrency.phase2.chapter09_guardedsuspendsion模式;

import java.util.Random;

/**
 * Created by lizhuquan on 2018/3/24.
 */
public class ServerThread extends Thread {

    private RequestQueue queue;

    private Random random;

    private volatile boolean closed = false;

    public ServerThread(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random();
    }

    @Override
    public void run() {
        while(!closed) {
            Request request = queue.getRequest();
            if (request == null) {
                System.out.println(">>>>> server receive null request, may be queue was interrupted or closed");
                continue;
            }
            System.out.println(">>>>> server receive request: " + request.getValue());
            try {
                Thread.sleep(random.nextInt(5_000));
            } catch (InterruptedException e) {
                System.out.println(">>>>> server was interrupted when processing request because server was force closing");
                return;
            }
        }
    }

    public void close() {
        this.closed = true;
        this.interrupt();
    }
}
