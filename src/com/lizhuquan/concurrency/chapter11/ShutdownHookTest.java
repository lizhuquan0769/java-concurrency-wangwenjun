package com.lizhuquan.concurrency.chapter11;

public class ShutdownHookTest {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("notify admin and release resources...");
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("application shutdown...");
        }, "shutdown hook thread"));

        new Thread(() -> {
            int i = 0;
            while (true) {
                System.out.println("application is working...");
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                if (i == 5) {
                    // 无论是进程结束，还是抛出异常导致结束，还是被进程被杀死（不是强制杀死，都会出发shutdownHook）
//                    break;
                    throw new RuntimeException("working timeout...");
                }

            }
        }).start();
    }
}
