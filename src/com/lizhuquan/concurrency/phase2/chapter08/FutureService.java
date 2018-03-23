package com.lizhuquan.concurrency.phase2.chapter08;

import java.util.function.Consumer;

/**
 * future代理人， 用于协调FutureTask和Future
 */
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> futureTask, Consumer<T> consumer) {
        AsyncFuture<T> future = new AsyncFuture<>();
        new Thread(() -> {
            T result = futureTask.call();
            future.setResult(result);
            if (consumer != null) {
                consumer.accept(result);
            }
        }).start();

        return future;
    }
}
