package com.lizhuquan.concurrency.phase2.chapter08;

public class FutureTest {

    public static void main(String[] args) throws InterruptedException {
        FutureService futureService = new FutureService();

        Future<String> future = futureService.submit(() -> {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        }, (result) -> {
            System.out.println(">>>>> auto call consumer, accept result:" + result);
        });

        System.out.println(">>>>> do other thing...");
        Thread.sleep(1_000);

        System.out.println(">>>>> custom accept result:" + future.get());
    }


}
