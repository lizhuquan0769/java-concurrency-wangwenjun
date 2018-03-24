package com.lizhuquan.concurrency.phase2.chapter12_balking模式;

import java.io.IOException;
import java.util.Random;

public class CustomerThread extends Thread {

    private final BalkingData balkingData;

    private final Random random = new Random();

    public CustomerThread(BalkingData balkingData) {
        super("Customer");
        this.balkingData = balkingData;
    }

    @Override
    public void run() {
        try {
            balkingData.save();
            for (int i = 0; i < 20; i++) {
                balkingData.change("No." + i);
                Thread.sleep(random.nextInt(1000));
                balkingData.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
