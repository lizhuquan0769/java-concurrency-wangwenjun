package com.lizhuquan.concurrency.phase3.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * CompletableFuture: 注册回调,不用主动调用结果
 * Created by lizhuquan on 2018/5/4.
 */
public class CompletableFutureExample {

    private static AtomicInteger counter = new AtomicInteger();

    private static void display(int data) {
        System.err.println(Thread.currentThread().getName() + " start to display:" + data);
        try {
            TimeUnit.SECONDS.sleep(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " display done:" + data);
    }

    private static int get() {
        int value = counter.incrementAndGet();
        System.err.println(Thread.currentThread().getName() + " start to get:" + value);
        try {
            TimeUnit.SECONDS.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " get done:" + value);
        return value;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 支持任务完成后自动回调
        /*CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println("done"));
        System.out.println("main");
        Thread.currentThread().join();*/



        // 支持流式多阶段任务
        /*IntStream.rangeClosed(1, 10).boxed()
                .forEach(i -> {
                    CompletableFuture.supplyAsync(CompletableFutureExample::get)
                            .thenAccept(CompletableFutureExample::display)
                            .whenComplete((v,t) -> System.out.println(i + " DONE"));
                });
        Thread.currentThread().join();*/



        // 主任务是obj, 次任务是str 和 i, 次任务搭配一个主词搭配完成后的runnable
        /*CompletableFuture.supplyAsync(Object::new).thenAcceptAsync(obj -> {
            System.out.println("obj[" + obj + "]=====>start");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("obj[" + obj + "]=====>end");
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> "HELLO").thenAcceptAsync(str -> {
                    System.out.println("str[" + str + "]=====>start");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("str[" + str + "]=====>end");
                })
                , () -> System.out.println("both 1 finish"))
                .runAfterBoth(CompletableFuture.supplyAsync(() -> 1).thenAcceptAsync(i -> {
                    System.out.println("i[" + i + "]=====>start");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("i[" + i + "]=====>end");
                }), () -> System.out.println("both 2 finish"));
        Thread.currentThread().join();*/




    }


}
