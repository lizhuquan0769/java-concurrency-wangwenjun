package com.lizhuquan.concurrency.chapter09;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.IntStream;

public class CaptureService {

    private static List<Thread> workers = new ArrayList<>();
    private static LinkedList<Thread> controllers = new LinkedList<>();
    private static final int MAX_WORKERS = 2;

    public static void main(String[] args) {

        Optional.of(">> capture begin...").ifPresent(System.out::println);

        Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10")
                .stream().map(CaptureService::createCaptureThread)
                .forEach(t -> {
                    workers.add(t);
                    t.start();
                });

        workers.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of(">> capture finish...").ifPresent(System.out::println);
    }

    private static Thread createCaptureThread(String name) {
        return new Thread(() -> {
            Optional.of(">> machine " + Thread.currentThread().getName() + " READY capture...").ifPresent(System.out::println);

            // wait or add worker to workers
            synchronized (controllers) {
                // while循环是为了唤醒之后继续抢，抢到则往下执行， 抢不到则继续wait
                while (controllers.size() >= MAX_WORKERS) {
                    try {
                        controllers.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                controllers.addLast(Thread.currentThread());
            }

            // do something
            Optional.of(">> machine " + Thread.currentThread().getName() + " BEGIN capture...").ifPresent(System.out::println);
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // remove worker from workers
            synchronized (controllers) {
                Optional.of(">> machine " + Thread.currentThread().getName() + " FINISH capture...").ifPresent(System.err::println);
                controllers.removeFirst();
                controllers.notifyAll();
            }
        }, name);
    }
}
