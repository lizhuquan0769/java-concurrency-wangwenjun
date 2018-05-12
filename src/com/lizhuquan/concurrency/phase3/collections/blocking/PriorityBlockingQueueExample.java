package com.lizhuquan.concurrency.phase3.collections.blocking;

import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by lizhuquan on 2018/5/7.
 * 无边界的优先级blocking queue
 * add offer put三个方法完全等价, 底层都是offer
 */
public class PriorityBlockingQueueExample {

    @Test
    public void testAdd() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(3);
        assertThat(queue.add("hello1"), is(true));
        assertThat(queue.add("hello2"), is(true));
        assertThat(queue.add("hello3"), is(true));
        assertThat(queue.add("hello4"), is(true));
        assertThat(queue.add("hello5"), is(true));
        assertThat(queue.size(), is(5));
    }

    @Test
    public void testGetElement() {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(3);
        assertThat(queue.add("hello4"), is(true));
        assertThat(queue.add("hello2"), is(true));
        assertThat(queue.add("hello3"), is(true));
        assertThat(queue.element(), is("hello2"));
    }
}
