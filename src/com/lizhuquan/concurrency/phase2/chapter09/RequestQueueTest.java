package com.lizhuquan.concurrency.phase2.chapter09;

import com.sun.security.ntlm.Server;

/**
 * guarded suspension(保护性暂挂模式)(队列缓冲)
 * Created by lizhuquan on 2018/3/24.
 */
public class RequestQueueTest {

    public static void main(String[] args) throws InterruptedException {
        RequestQueue queue = new RequestQueue();
        ServerThread server = new ServerThread(queue);
        ClientThread client = new ClientThread(queue);

        server.start();
        client.start();;

        Thread.sleep(10_000);

        server.close();
    }
}
