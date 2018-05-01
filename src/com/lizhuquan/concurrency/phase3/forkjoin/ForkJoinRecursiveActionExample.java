package com.lizhuquan.concurrency.phase3.forkjoin;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ForkJoinRecursiveActionExample {

    private final static AtomicInteger SUM = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Void> future = forkJoinPool.submit(new CalculatedRecursiveAction(0, 100));
        future.join();
        System.out.println(SUM.get());
    }

    static class CalculatedRecursiveAction extends RecursiveAction {

        private final static int MAX_THRESHOLD = 10;
        private int start;
        private int end;

        public CalculatedRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_THRESHOLD) {
                System.out.println("[" + start + "," + end + "]");
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start + end) / 2;
                CalculatedRecursiveAction leftAction = new CalculatedRecursiveAction(start, middle);
                CalculatedRecursiveAction rightAction = new CalculatedRecursiveAction(middle + 1, end);
                leftAction.fork();
                rightAction.fork();
                leftAction.join();
                rightAction.join();
            }
        }
    }
}
