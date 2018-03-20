package com.lizhuquan.concurrency.phase1.chapter10;

import java.util.Collection;

public interface Lock {

    void lock() throws InterruptedException;

    void lock(long mills) throws TimeoutException, InterruptedException;

    void unlock();

    Collection<Thread> getBlockThread();

    int getBlockSize();
}
