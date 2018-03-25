package com.lizhuquan.concurrency.phase2.chapter15_threadpermessage模式;

public class Message {

    private String value;

    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
