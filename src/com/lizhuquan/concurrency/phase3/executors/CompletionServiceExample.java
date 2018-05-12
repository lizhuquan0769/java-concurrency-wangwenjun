package com.lizhuquan.concurrency.phase3.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by lizhuquan on 2018/5/3.
 */
public class CompletionServiceExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorCompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);

        IntStream.rangeClosed(1,10).boxed().forEach(i -> {
            completionService.submit(() -> {
                TimeUnit.SECONDS.sleep(i);
                System.out.println("task " + i + " finished");
                return i;
            });
        });

        Future<Integer> future = null;
        while ((future = completionService.take()) != null) {
            System.out.println("catch result " + future.get());
        }
    }
}
