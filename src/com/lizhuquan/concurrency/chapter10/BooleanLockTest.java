package com.lizhuquan.concurrency.chapter10;

import java.util.Arrays;
import java.util.Optional;

public class BooleanLockTest {

    public static void main(String[] args) {
        final BooleanLock lock = new BooleanLock();

        Arrays.asList("T1", "T2", "T3", "T4")
                .stream()
                .forEach(t -> {
                    new Thread(() -> {
                        try {
                            lock.lock(13_000);
                            work();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlock();
                        }
                    }, t).start();
                });
    }

    private static void work() throws InterruptedException {
        Optional.of(">>>thread " + Thread.currentThread().getName() + " is working").ifPresent(System.out::println);
        Thread.sleep(5_000);
    }
}
