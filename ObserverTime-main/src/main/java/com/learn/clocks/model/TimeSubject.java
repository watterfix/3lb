package com.learn.clocks.model;

public interface TimeSubject {
    void addObserver(TimeObserver observer);
    void removeObserver(TimeObserver observer);
}
