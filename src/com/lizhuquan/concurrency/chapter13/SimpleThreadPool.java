package com.lizhuquan.concurrency.chapter13;

import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleThreadPool {

    private final int initSize;

    private final static int DEFAULT_INIT_SIZE = 10;

    private volatile static int seq = 0;

    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-";

    private final static ThreadGroup DEFAULT_GROUP = new ThreadGroup("POOL_GROUP");

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>();

    public SimpleThreadPool() {
        this(DEFAULT_INIT_SIZE);
    }

    public SimpleThreadPool(int initSize) {
        this.initSize = initSize;
        init();
    }

    private void init() {
        for (int i = 0; i < initSize; i++) {
            createWorkerTask();
        }
    }

    private void createWorkerTask() {
        WorkerTask task = new WorkerTask(DEFAULT_GROUP, THREAD_PREFIX + seq++);
        task.start();
        THREAD_QUEUE.add(task);
    }

    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    private static enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    private static class WorkerTask extends Thread {

        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask (ThreadGroup group, String name) {
             super(group, name);
        }

        @Override
        public void run() {
            OUTER:
            while(this.taskState != TaskState.DEAD) {
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

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool pool = new SimpleThreadPool();

        IntStream.range(0, 20)
                .forEach(i -> {
                    pool.submit(() -> {
                        System.out.println("the runable " + i + " be serviced by " + Thread.currentThread().getName() + " start");
                        try {
                            Thread.sleep(5_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.err.println("the runable " + i + " be serviced by " + Thread.currentThread().getName() + " finish");
                    });
                });

        for (int i = 0; i < 100; i++) {
            final int index = i;
            pool.submit(() -> {
                System.out.println("the other runable " + index + " be serviced by " + Thread.currentThread().getName() + " start");
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.err.println("the other runable " + index + " be serviced by " + Thread.currentThread().getName() + " finish");
            });

            Thread.sleep(1_000);
        }
    }
}
