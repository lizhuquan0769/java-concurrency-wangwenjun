package com.lizhuquan.concurrency.phase3.lock_and_condition;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 *  测试报告
 *      5R5W：STAMPED > RWLOCK > SYNC > OPTIMISTIC
 *      10R10W: STAMPED > SYNC > OPTIMISTIC > RWLOCK
 *      16R4W: STAMPED > OPTIMISTIC > SYNC > RWLOCK
 *      19R1W: OPTIMIC > SYNC > STAMPED > RWLOCK
 *  测试总结
 *      少量读写并发：选择STAMPED或者RWLOCK
 *      大量读写并发：选择STAMPED或者SYNC
 *
 *      读写并发有点差距：选择STAMPED或者SYNC
 *      读写并发有较大差距：选择OPTIMISTIC或者SYNC
 *
 *      RWLOCK在读写线程差距较大情况下会导致写线程饥饿，不能使用
 *      STAMPED绝大多数情况下性能比RWLOCK好， 在1.8版本中可替换RWLOCK
 *      OPTIMITIC在读写线程差距悬殊的时候可以选择，1.7版本可用SYNC
 *      SYNC中规中矩， 任何情况可选择
 */
public class StampedLockExample {

    private static double x,y;
    private final static StampedLock sl = new StampedLock();

    public static void main(String[] args) {

        Random rd = new Random();

        IntStream.rangeClosed(1,1).boxed().forEach(i -> {
            new Thread("MOVE-" + i) {
                @Override
                public void run() {
                    while (true) {
                        double deltaX = rd.nextInt(10) * 1.0d;
                        double deltaY = rd.nextInt(10) * 1.0d;
                        move(deltaX, deltaY);
//                        System.err.println(Thread.currentThread().getName()
//                                + " >>>>>>>>>>>>>>>>>>>>>>>>> delta x=" + x + " y=" + y + " success <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        });

        IntStream.rangeClosed(1,500).boxed().forEach(i -> {
            new Thread("READ-" + i) {
                @Override
                public void run() {
                    while (true) {
                        double distance = distanceFromOrigin();
//                        System.out.println(Thread.currentThread().getName() + " >>> distance=" + distance);
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

    // 悲观写锁
    private static void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // 乐观读锁 + 悲观读锁
    private static double distanceFromOrigin() {
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        if (!sl.validate(stamp)) {
            System.out.println(Thread.currentThread().getName() + "*********************************************** stamp had been modify ***************************************");
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    // 悲观读锁 + 读锁转写锁

}
