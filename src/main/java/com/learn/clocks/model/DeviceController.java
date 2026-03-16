package com.learn.clocks.model;

public interface DeviceController extends TimeObserver {
    void start();
    void stop();
    String getTitle();
    String getDisplayText();
}