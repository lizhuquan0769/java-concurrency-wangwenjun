package com.lizhuquan.concurrency.chapter10;

import java.util.Collection;

public interface Lock {

    void lock() throws InterruptedException;

    void lock(long mills) throws TimeoutException;

    void unlock();

    Collection<Thread> getBlockThread();

    int getBlockSize();
}
