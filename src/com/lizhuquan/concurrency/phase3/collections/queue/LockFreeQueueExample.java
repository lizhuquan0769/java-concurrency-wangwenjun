package com.lizhuquan.concurrency.phase3.collections.queue;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * Created by lizhuquan on 2018/5/10.
 */
public class LockFreeQueueExample {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Long, Object> data = new ConcurrentHashMap<>();
        LockFreeQueue<Long> queue = new LockFreeQueue<>();
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService service = Executors.newFixedThreadPool(10);

        IntStream.rangeClosed(1, 5).boxed().map(l -> {
           return (Runnable) () -> {
                while (counter.getAndIncrement() <= 100) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    queue.addLast(System.nanoTime());
                }
           } ;
        }).forEach(service::submit);

        IntStream.rangeClosed(1, 5).boxed().map(l -> {
           return (Runnable) () -> {
               while (counter.getAndDecrement() > 0) {
                   try {
                       TimeUnit.MILLISECONDS.sleep(100);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   Long value = queue.removeFirst();
                   if (value == null) {
                       continue;
                   }
                   System.out.println(queue.removeFirst());
               }
           };
        }).forEach(service::submit);

        service.shutdown();
        service.awaitTermination(1L, TimeUnit.HOURS);
    }

    private static class Node<E> {

        private E element;
        private volatile Node<E> next;

        public Node(E element) {
            this.element = element;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return element == null ? "" : element.toString();
        }
    }

    public static class LockFreeQueue<E> {

        private AtomicReference<Node<E>> head,tail;

        private AtomicInteger size = new AtomicInteger(0);

        public LockFreeQueue() {
            Node<E> node = new Node<>(null);
            this.head = new AtomicReference<>(node);
            this.tail = new AtomicReference<>(node);
        }

        public void addLast(E e) {
            if (e == null) {
                throw new NullPointerException("element is null");
            }
            Node<E> newNode = new Node<>(e);
            Node<E> prevNode = tail.getAndSet(newNode);
            prevNode.setNext(newNode); // Node.next设置为volatile是为了防止重排序, size.incrementAndGet()必须排在后面执行
            size.incrementAndGet();
        }

        public E removeFirst() {
            Node<E> headNode,valueNode;
            do {
                headNode = head.get();
                valueNode = headNode.next;
            } while (valueNode != null && !head.compareAndSet(headNode, valueNode));

            E value = (valueNode != null ? valueNode.getElement() : null);
            if (valueNode != null) { // 回收内存
                valueNode.element = null;
            }
            size.decrementAndGet();
            return value;
        }

        public synchronized boolean isEmpty() {
            return size.get() == 0;
        }
    }

}
