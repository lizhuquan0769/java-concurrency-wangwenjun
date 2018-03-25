package com.lizhuquan.concurrency.phase2.chapter16_twophase2;

import java.io.IOException;
import java.net.Socket;

public class AppClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        AppServer server = new AppServer(13345);
        server.start();

        Thread.sleep(15_000);

        server.shutdown();
    }
}
