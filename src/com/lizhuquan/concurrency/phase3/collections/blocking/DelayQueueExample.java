package com.lizhuquan.concurrency.phase3.collections.blocking;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by lizhuquan on 2018/5/7.
 * 内部实现为PriorityQueue
 *
 * Q:
 *  1. the delay queue will orderd by expired time? 【yes】
 *  2. when the empty delay queue is empty, the null value will return when use poll method? and when take method? 【take will waiting， but poll/remove will return null if expireTime not reach】
 *  3. when the dealy time had arrived, the head element will return quickly? 【yes】
 *  4. use iterator can get the unexpire elements? 【yes】
 *
 *  NOTE:
 *      the delay queue elements must implement interface {@link java.util.concurrent.Delayed}
 *      the delay queue is unbounded  queue.
 */

public class DelayQueueExample {

    public static <T extends Delayed> DelayQueue<T> create() {
        return new DelayQueue<>();
    }

    /**
     * 1. add method must add the delayed element
     * 2. peek method will return null/element quickly
     * 3.
     */
    @Test
    public void testAddAndPeek() {
        DelayQueue<DelayElement<String>> queue = create();
        DelayElement<String> delay1 = DelayElement.of("delay1", 5000);
        queue.put(delay1);
        Assert.assertThat(queue.size(), CoreMatchers.is(1)) ;
        long start = System.currentTimeMillis();
        Assert.assertThat(queue.peek(), CoreMatchers.is(delay1)) ;
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testIteratorAndCheckSort() {
        DelayQueue<DelayElement<String>> queue = create();
        queue.add(DelayElement.of("delay2", 3000));
        queue.add(DelayElement.of("delay1", 4000));
        queue.add(DelayElement.of("delay4", 1000));
        queue.add(DelayElement.of("delay3", 2000));

        long start = System.currentTimeMillis();
        Iterator<DelayElement<String>> iterator = queue.iterator();
        for (;iterator.hasNext();) {
            System.out.println(iterator.next());
        }
        long end = System.currentTimeMillis();
        System.out.println("cost: " + (end - start));
        Assert.assertThat((end - start) <  5, CoreMatchers.is(true));
    }


    private static class DelayElement<E> implements Delayed {

        private final E e;
        private final long expireTime;

        public DelayElement(E e, long delayMillMS) {
            this.e = e;
            this.expireTime = System.currentTimeMillis() + delayMillMS;
        }

        public static <E> DelayElement<E> of(E e, long delay) {
            return new DelayElement<>(e, delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = expireTime - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            DelayElement<E> that = (DelayElement<E>)o;
            if (this.expireTime < that.getExpireTime()) {
                return -1;
            } else if (this.expireTime > that.getExpireTime()) {
                return 1;
            } else {
                return 0;
            }
        }

        public E getData() {
            return e;
        }

        public long getExpireTime() {
            return expireTime;
        }

        @Override
        public String toString() {
            return "DelayElement{" +
                    "e=" + e +
                    ", expireTime=" + expireTime +
                    '}';
        }
    }
}
