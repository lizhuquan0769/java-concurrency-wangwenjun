package com.lizhuquan.concurrency.phase2.chapter15_threadpermessage模式;

import java.util.stream.IntStream;

public class ThreadPreMessageTest {

    public static void main(String[] args) {

        MessageHandler handler = new MessageHandler();

        IntStream.rangeClosed(1, 50).forEach(i -> {
            handler.handle(new Message("Message-" + i));
        });

        handler.shutdown();
    }
}
