package com.lizhuquan.concurrency.phase1.chapter13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lizhuquan
 *         拒绝策略
 */
public class SimpleThreadPool extends Thread {

    private final static int DEFAULT_TASK_QUEUE_SIZE = 1000;

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("队列已满, 请求被拒绝");
    };

    private final static String DEFAULT_THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    private final DiscardPolicy discardPolicy;

    private int size;

    private int min;

    private int active;

    private int max;

    private final int queueSize;

    private volatile boolean shutdown;

    private volatile boolean destory;

    private volatile static int seq = 0;

    private final static ThreadGroup THREAD_GROUP = new ThreadGroup("POOL_GROUP");

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool() {
        this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for (int i = 0; i < min; i++) {
            createWorkerTask();
        }
        this.size = min;
        this.start();
    }

    @Override
    public void run() {
        while (!destory) {
            try {
                System.out.printf("thread pool Min:%d, Active:%d, Max:%d, Current:%d, QueueSize:%d\n",
                        this.min, this.active, this.max, this.size, TASK_QUEUE.size());

                Thread.sleep(2_000);

                // 扩容到active
                if (size < active && TASK_QUEUE.size() > active) {
                    for (int i = size; i < active; i++) {
                        createWorkerTask();
                    }
                    System.err.printf(">>>>>the thread pool incrementd to active size\n");
                    this.size = active;
                } else if (size < max && TASK_QUEUE.size() > max) {
                    for (int i = size; i < max; i++) {
                        createWorkerTask();
                    }
                    System.err.printf(">>>>>the thread pool incrementd to max size\n");
                    this.size = max;
                }

                synchronized (THREAD_QUEUE) {
                    // 释放线程到active
                    if (TASK_QUEUE.isEmpty() && size > active) {
                        System.err.println(">>>>>======= reduce thread pool start=========");

                        int releaseSize = size - active;
                        for (Iterator<WorkerTask> iterator = THREAD_QUEUE.iterator(); iterator.hasNext(); ) {
                            if (releaseSize <= 0) {
                                break;
                            }

                            WorkerTask task = iterator.next();
                            task.close();
                            task.interrupt();
                            iterator.remove();
                            releaseSize--;
                            System.err.println(">>>>>the thread " + task.getName() + " had been close...");
                        }
                        size = active;

                        System.err.println(">>>>>======= reduce thread pool finish=========");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void createWorkerTask() {

        WorkerTask task = new WorkerTask(THREAD_GROUP, DEFAULT_THREAD_PREFIX + seq++);
        task.start();
        THREAD_QUEUE.add(task);
    }

    public void submit(Runnable runnable) {

        // 首先判断是否中断了, 是的话禁止提交任务
        if (shutdown) {
            throw new IllegalStateException("the thread pool already shutdown and not allow submit task...");
        }

        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() >= queueSize) {
                discardPolicy.discard();
            } else {
                TASK_QUEUE.addLast(runnable);
                TASK_QUEUE.notifyAll();
            }
        }
    }

    public void shutdown() throws InterruptedException {

        if (shutdown) {
            throw new IllegalStateException("the thread pool had receive shutdown cmd, don't do again");
        }

        // 首先标记
        shutdown = true;
        System.out.println("the thread pool already shutdown...");

        new Thread() {
            @Override
            public void run() {
                // 先判断任务队列是否为空
                while (!TASK_QUEUE.isEmpty()) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (THREAD_QUEUE) {
                    // 判断全部线程是否工作完毕
                    int currThreadSize = THREAD_QUEUE.size();
                    while (currThreadSize > 0) {
                        for (WorkerTask task : THREAD_QUEUE) {
                            if (task.getTaskState() == TaskState.BLOCKED) {
                                currThreadSize--;
                                task.close();
                                task.interrupt();
                            } else {
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    destory = true;
                    System.out.println("the thread pool already destory...");
                }
            }
        }.start();
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public boolean isShutdown() {
        return this.shutdown;
    }

    public boolean isDestory() {
        return destory;
    }

    public int getMin() {
        return min;
    }

    public int getActive() {
        return active;
    }

    public int getMax() {
        return max;
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    private static class WorkerTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }

                    runnable = TASK_QUEUE.removeFirst();
                }

                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public TaskState getTaskState() {
            return taskState;
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }
    }


}
