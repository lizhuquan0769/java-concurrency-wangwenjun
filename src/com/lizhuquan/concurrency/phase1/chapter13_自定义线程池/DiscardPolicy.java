package com.lizhuquan.concurrency.phase1.chapter13_自定义线程池;

/**
 * Created by lizhuquan on 2018/3/20.
 */
public interface DiscardPolicy {
    void discard() throws DiscardException;
}
