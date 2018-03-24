package com.lizhuquan.concurrency.phase2.chapter08_future模式;

/**
 * 封装要处理的任务
 */
public interface FutureTask<T> {

    T call();
}
