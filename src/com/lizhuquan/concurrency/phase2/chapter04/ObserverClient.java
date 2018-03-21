package com.lizhuquan.concurrency.phase2.chapter04;

public class ObserverClient {

    public static void main(String[] args) throws InterruptedException {
        final Subject subject = new Subject();
        BinaryObserver binaryObserver = new BinaryObserver(subject);
        OctalObserver octalObserver = new OctalObserver(subject);

        subject.setState(1);
        Thread.sleep(5_000);
        subject.setState(2);
        Thread.sleep(5_000);
        subject.setState(3);
        Thread.sleep(5_000);
        subject.setState(4);
        Thread.sleep(5_000);
    }
}
