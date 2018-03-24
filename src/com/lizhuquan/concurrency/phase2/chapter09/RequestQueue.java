package com.lizhuquan.concurrency.phase2.chapter09;

import java.util.LinkedList;

/**
 * Created by lizhuquan on 2018/3/24.
 */
public class RequestQueue {

    private LinkedList<Request> queue = new LinkedList<>();

    public void putRequest(Request request)  {
        synchronized (queue) {
            queue.addLast(request);
            queue.notifyAll();
        }
    }

    public Request getRequest() {
        synchronized (queue) {
            while (queue.size() == 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    System.out.println(">>>>> getRequest() be interrupted, will return null ");
                    return null;
                }
            }

            Request request = queue.removeFirst();
            return request;
        }
    }
}
