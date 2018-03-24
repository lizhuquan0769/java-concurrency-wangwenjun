package com.lizhuquan.concurrency.phase2.chapter07;

/**
 * Created by lizhuquan on 2018/3/23.
 */
public class ImmutablePersonTest {

    public static void main(String[] args) {

        Person person = new Person("lizhuquan", 1);

        new Thread(() -> {
            while (true) {
                System.out.println(person);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                System.out.println(person);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println(0x11);
        System.out.println(011);
        System.out.println(0b11);
    }
}
