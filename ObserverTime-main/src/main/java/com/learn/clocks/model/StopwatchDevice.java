package com.learn.clocks.model;

public class StopwatchDevice implements DeviceController {
    private long startCentralTime = 0;
    private long elapsed = 0;
    private boolean running = false;
    private long lastTickTime = 0;

    @Override
    public void onTick(long centralTimeSeconds) {
        lastTickTime = centralTimeSeconds;
        if (running) {
            elapsed = centralTimeSeconds - startCentralTime;
        }
    }

    @Override
    public void start() {
        if (!running) {
            running = true;
            if (elapsed == 0) {
                startCentralTime = lastTickTime;
            } else {
                startCentralTime = lastTickTime - elapsed;
            }
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    public void reset() {
        elapsed = 0;
        startCentralTime = 0;
        running = false;
    }

    @Override
    public String getTitle() {
        return "Секундомер";
    }

    @Override
    public String getDisplayText() {
        return running ? "▶ " + elapsed + " сек" : elapsed + " сек";
    }
}