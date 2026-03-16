package com.learn.clocks.model;

public interface TimeObserver {
    void onTick(long timeSeconds);
}
