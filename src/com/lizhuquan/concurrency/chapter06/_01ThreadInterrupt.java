package com.lizhuquan.concurrency.chapter06;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 线程打断 和 优雅的关闭线程
 */
public class _01ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(){
            @Override
            public void run() {
                while (true) {
                    System.out.println();
                    System.out.println(">>t1 running on " + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        System.out.println();
                        System.out.println(">>线程t1被打断, 捕获异常后，继续往下执行...");
                        System.out.println(">>线程状态：" + this.getState());
                        System.out.println(">>线程中断状态：" + this.isInterrupted());
                        System.out.println(">>线程是否存活：" + this.isAlive());
                        // 可以通过break掉当前循环来优雅的结束线程
                        break;
                    }
                }
            }
        };
        t1.start();

        while (true) {
            Thread.sleep(2000);
            System.out.println();
            System.out.println("中断前t1的中断状态：" + t1.isInterrupted());
            t1.interrupt();
            System.out.println("中断后t1的中断状态：" + t1.isInterrupted());
        }
    }
}
