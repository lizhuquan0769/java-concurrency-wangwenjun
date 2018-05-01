package com.lizhuquan.concurrency.phase3.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ForkJoinRecursiveTaskExample {

    public static void main(String[] args){
        int start = 0;
        int end = 1000;

        long begin = System.currentTimeMillis();

        int sum1 = IntStream.rangeClosed(start,end).sum();
        long step1 = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> future = forkJoinPool.submit(new CalculatedRecursiveTask(start, end));
        int sum2 = 0;
        try {
            sum2 = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long step2 = System.currentTimeMillis();

        System.out.println("step1:" + sum1 + ":" + (step1 - begin));
        System.out.println("step2:" + sum2 + ":" + (step2 - step1));
    }

    static class CalculatedRecursiveTask extends RecursiveTask<Integer> {

        private final static int MAX_THRESHOLD= 5;
        private int start;
        private int end;

        public CalculatedRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= MAX_THRESHOLD) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (end + start) / 2;
                CalculatedRecursiveTask leftTask = new CalculatedRecursiveTask(start, middle);
                CalculatedRecursiveTask rightTask = new CalculatedRecursiveTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
                return leftTask.join() + rightTask.join();
            }
        }
    }
}
