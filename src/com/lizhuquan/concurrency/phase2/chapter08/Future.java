package com.lizhuquan.concurrency.phase2.chapter08;

/**
 * 凭据，用来未来获取处理结果
 * @param <T>
 */
public interface Future<T> {

    T get() throws InterruptedException;
}
