package com.lizhuquan.concurrency.chapter11;

public class ShutdownHookTest {

    public static void main(String[] args) {

        Thread shutdownProcessor = new Thread(() -> {
            System.out.println("notify admin and release resources...");
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("application shutdown...");
        }, "shutdown hook thread");

        // 设置shutdown钩子
        Runtime.getRuntime().addShutdownHook(shutdownProcessor);



       Thread workThread = new Thread(() -> {
            int i = 0;
            while (true) {
                System.out.println("application is working...");

                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    System.err.println("Catch Thread Execption In Try-Catch Block...");
                    e.printStackTrace();
                }

                System.out.println(1 / 0);

                i++;
                if (i == 5) {
                    // 无论是进程结束，还是抛出异常导致结束，还是被进程被杀死（不是强制杀死，都会出发shutdownHook）
//                    break;
                    throw new RuntimeException("working timeout...");
                }

            }
        }, "t1");

        // 设置非捕捉异常(RuntimeException)处理器
        workThread.setUncaughtExceptionHandler((t, e) -> {
            System.err.println("Catch Thread Execption In UnCaughtExceptionHandler...");
            System.err.println("thread -> " + t.getName());
            System.err.println("exception -> " + e.getMessage());
        });

       workThread.start();

    }
}
