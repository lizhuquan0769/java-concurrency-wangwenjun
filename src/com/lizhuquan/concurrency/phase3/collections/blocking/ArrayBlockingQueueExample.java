package com.lizhuquan.concurrency.phase3.collections.blocking;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by lizhuquan on 2018/5/7.
 */
public class ArrayBlockingQueueExample {

    /**
     * add method: 超出容量会抛异常
     * 相对: remove
     */
    @Test
    public void testAddWithoutException() {
        int size = 2;
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(size);
        Assert.assertThat(queue.add("hello1"), CoreMatchers.is(true));
        Assert.assertThat(queue.add("hello2"), CoreMatchers.is(true));
        Assert.assertThat(queue.size(), CoreMatchers.is(size));
    }
    @Test(expected = IllegalStateException.class)
    public void testAddWithException() {
        int size = 2;
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(size);
        Assert.assertThat(queue.add("hello1"), CoreMatchers.is(true));
        Assert.assertThat(queue.add("hello2"), CoreMatchers.is(true));
        Assert.assertThat(queue.add("hello3"), CoreMatchers.is(true));
        Assert.assertThat(queue.size(), CoreMatchers.is(size));
    }


    /**
     * offer method: 超出容量会返回false
     * 相对: poll
     */
    @Test
    public void testOffer() {
        int size = 2;
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(size);
        Assert.assertThat(queue.offer("hello1"), CoreMatchers.is(true));
        Assert.assertThat(queue.offer("hello2"), CoreMatchers.is(true));
        Assert.assertThat(queue.offer("hello3"), CoreMatchers.is(false));
        Assert.assertThat(queue.size(), CoreMatchers.is(size));
    }

    /**
     * put method: 超出容量会阻塞
     * 相对: take
     */
    @Test
    public void testPut() throws InterruptedException {
        int size = 2;
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(size);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            try {
                Assert.assertThat(queue.take(), CoreMatchers.is("hello1"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();

        queue.put("hello1");
        queue.put("hello2");
        queue.put("hello3");
    }
}
