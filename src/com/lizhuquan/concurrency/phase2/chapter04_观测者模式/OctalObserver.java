package com.lizhuquan.concurrency.phase2.chapter04_观测者模式;

public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println(OctalObserver.class.getName() + ": " + Integer.toOctalString(subject.getState()));
    }
}
