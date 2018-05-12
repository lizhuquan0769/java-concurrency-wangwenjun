package com.lizhuquan.concurrency.phase2.chapter17_woker模式;

import java.util.Arrays;

/**
 * 工作车间
 * Created by lizhuquan on 2018/3/30.
 */
public class Channel {

    private final static int MAX_REQUEST = 100;

    private final Request[] requestArray;

    private int head;

    private int tail;

    private int index;

    private final WorkerThread[] workerPool;

    public Channel(int workers, int maxRequest) {
        this.requestArray = new Request[maxRequest];
        this.head = 0;
        this.tail = 0;
        this.index = 0;
        this.workerPool = new WorkerThread[workers];
        this.init();
    }

    public Channel(int workers) {
        this(workers, MAX_REQUEST);
    }

    private void init() {
        Arrays.asList();
    }
}
