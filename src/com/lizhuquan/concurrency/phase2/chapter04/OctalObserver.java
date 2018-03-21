package com.lizhuquan.concurrency.phase2.chapter04;

public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println(OctalObserver.class.getName() + ": " + Integer.toOctalString(subject.getState()));
    }
}
