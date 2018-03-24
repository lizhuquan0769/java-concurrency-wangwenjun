package com.lizhuquan.concurrency.phase2.chapter08_future模式;

import java.util.function.Consumer;

/**
 * future代理人， 用于协调FutureTask和Future
 */
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> futureTask) {
        AsyncFuture<T> future = new AsyncFuture<>();
        new Thread(() -> {
            T result = futureTask.call();
            future.setResult(result);
        }).start();

        return future;
    }

    public <T> void submit(final FutureTask<T> futureTask, Consumer<T> consumer) {
        new Thread(() -> {
            T result = futureTask.call();
            consumer.accept(result);
        }).start();
    }
}
