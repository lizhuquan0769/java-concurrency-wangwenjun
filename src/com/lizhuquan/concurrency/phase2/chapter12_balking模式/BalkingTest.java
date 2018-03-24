package com.lizhuquan.concurrency.phase2.chapter12_balking模式;

/**
 * balking design pattern
 */
public class BalkingTest {

    public static void main(String[] args) {
        BalkingData balkingData = new BalkingData("D:\\balking.txt","===BEGIN===");
        new CustomerThread(balkingData).start();
        new WaiterThread(balkingData).start();
    }
}
