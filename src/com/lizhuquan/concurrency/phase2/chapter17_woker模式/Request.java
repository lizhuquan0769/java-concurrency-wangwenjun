package com.lizhuquan.concurrency.phase2.chapter17_woker模式;

/**
 * Created by lizhuquan on 2018/3/30.
 */
public class Request {

    private final String name;

    private final int number;

    public Request(final String name, final int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executed " + this);
    }

    @Override
    public String toString() {
        return "Request=> No. " + number + " Name. " + name;
    }
}
