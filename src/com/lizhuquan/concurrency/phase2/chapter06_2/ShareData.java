package com.lizhuquan.concurrency.phase2.chapter06_2;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ShareData {

    private volatile char[] buffer;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    ShareData(int size) {
        this.buffer = new char[size];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = '*';
        }
    }

    public void read() throws InterruptedException {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " reads " + String.valueOf(this.doRead()));
        } finally {
            readLock.unlock();
        }
    }

    public void write(char c) throws InterruptedException {
        try {
            writeLock.lock();
            System.out.println(">>>>>> writing " + c);
            this.doWrite(c);
        } finally {
            writeLock.unlock();
        }
    }

    private char[] doRead() {
        char[] newBuff = new char[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            newBuff[i] = buffer[i];
        }
        slowly(10);
        return newBuff;
    }

    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
            slowly(50);
        }
    }

    private void slowly(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
