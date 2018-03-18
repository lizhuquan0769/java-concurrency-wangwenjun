package com.lizhuquan.concurrency.chapter10;

public class TimeoutException extends Exception {

    public TimeoutException(String message) {
        super(message);
    }
}
