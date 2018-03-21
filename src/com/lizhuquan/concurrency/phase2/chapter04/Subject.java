package com.lizhuquan.concurrency.phase2.chapter04;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题
 */
public class Subject {

    private List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        if (state == this.state) {
            return;
        }
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        observers.stream().forEach(Observer::update);
    }
}
