package com.lizhuquan.concurrency.phase3.lock_and_condition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockExample {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition notFull = lock.newCondition();
    private static Condition notEmpty = lock.newCondition();
    private static LinkedList<Long> data = new LinkedList<>();
    private static Integer MAX_DATA_SIZE = 5;

    public static void main(String[] args) {

        IntStream.rangeClosed(1, 6).boxed().forEach(i -> {
            new Thread("WRITE-" + i) {
                @Override
                public void run() {
                    while (true) {
                        writeDate();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });

        IntStream.rangeClosed(1, 3).boxed().forEach(i -> {
            new Thread("READ-" + i) {
                @Override
                public void run() {
                    while (true) {
                        readData();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });
    }

    private static void writeDate() {
        try {
            lock.lock();

            while(data.size() == MAX_DATA_SIZE) {
                notFull.await();
            }

            long value = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "->" + value);
            data.addLast(value);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void readData() {
        try {
            lock.lock();
            while (data.isEmpty()) {
                notEmpty.await();
            }

            System.err.println(Thread.currentThread().getName() + "->" + data.removeFirst());
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
