package com.lizhuquan.concurrency.phase2.chapter04;

/**
 * 订阅者
 */
public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();
}
