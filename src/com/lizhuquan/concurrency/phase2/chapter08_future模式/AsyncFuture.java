package com.lizhuquan.concurrency.phase2.chapter08_future模式;

public class AsyncFuture<T> implements Future<T> {

    private volatile boolean done = false;

    private T result = null;

    public void setResult(T t) {
        synchronized (this) {
            this.result = t;
            this.done = true;
            this.notifyAll();
        }
    }

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            if (!done) {
                this.wait();
            }
            return result;
        }
    }
}
