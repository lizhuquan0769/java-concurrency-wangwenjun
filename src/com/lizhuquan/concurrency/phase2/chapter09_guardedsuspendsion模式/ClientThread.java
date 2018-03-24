package com.lizhuquan.concurrency.phase2.chapter09_guardedsuspendsion模式;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by lizhuquan on 2018/3/24.
 */
public class ClientThread extends Thread {

    private RequestQueue queue;

    private Random random;

    public ClientThread(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random();
    }

    @Override
    public void run() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Request request = new Request(i + "");
            queue.putRequest(request);
            System.out.println(">>>>> client send request: " + request.getValue());
            try {
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
