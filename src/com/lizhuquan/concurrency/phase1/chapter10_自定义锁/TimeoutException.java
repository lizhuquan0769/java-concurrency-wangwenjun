package com.lizhuquan.concurrency.phase1.chapter10_自定义锁;

public class TimeoutException extends Exception {

    public TimeoutException(String message) {
        super(message);
    }
}
