package com.lizhuquan.concurrency.phase2.chapter09_guardedsuspendsion模式;

/**
 * Created by lizhuquan on 2018/3/24.
 */
public class Request {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Request(String value) {

        this.value = value;
    }
}
