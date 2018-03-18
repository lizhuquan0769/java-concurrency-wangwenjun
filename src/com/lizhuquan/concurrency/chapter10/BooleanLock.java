package com.lizhuquan.concurrency.chapter10;

import javax.swing.text.html.Option;
import java.util.*;

public class BooleanLock implements Lock {

    private boolean initValue;

    private Collection<Thread> blockThreadCollection = new ArrayList<>();

    private Thread currLockThread;

    @Override
    public synchronized void lock() throws InterruptedException {

        Thread currThread = Thread.currentThread();
        while (initValue) {
            if (!blockThreadCollection.contains(currThread)) {
                blockThreadCollection.add(currThread);
            }
            Optional.of(">>>thread " + currThread.getName() + " wait for lock").ifPresent(System.err::println);
            this.wait();
        }

        initValue = true;
        currLockThread = currThread;
        Optional.of(">>>thread " + currThread.getName() + " get the lock ").ifPresent(System.out::println);
    }

    @Override
    public synchronized void lock(long mills) throws TimeoutException, InterruptedException {

        if (mills <= 0) {
            lock();
        } else {
            Thread currThread = Thread.currentThread();
            long deadTime = System.currentTimeMillis() + mills;
            while (initValue) {

                long remainTime = deadTime - System.currentTimeMillis();
                if (remainTime <= 0) {
                    throw new TimeoutException(">>>thread " + currThread.getName() + " is timeout ");
                }
                Optional.of(">>>thread " + currThread.getName() + " remainTime:" + remainTime + " to wait for the lock").ifPresent(System.out::println);

                if (!blockThreadCollection.contains(currThread)) {
                    blockThreadCollection.add(currThread);
                }

                Optional.of(">>>thread " + currThread.getName() + " wait for lock").ifPresent(System.err::println);
                this.wait(remainTime);
                // 当前线程得到要么被notify， 要么超时，才会往下执行
            }

            initValue = true;
            currLockThread = currThread;
            Optional.of(">>>thread " + currThread.getName() + " get the lock ").ifPresent(System.out::println);
        }
    }

    @Override
    public synchronized  void unlock() {

        Thread currThread = Thread.currentThread();
        if (currThread == currLockThread) {
            this.initValue = false;
            blockThreadCollection.remove(currThread);
            Optional.of(">>>thread " + currThread.getName() + " release the lock ").ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockThread() {
        return Collections.unmodifiableCollection(blockThreadCollection);
    }

    @Override
    public int getBlockSize() {
        return blockThreadCollection.size();
    }
}
