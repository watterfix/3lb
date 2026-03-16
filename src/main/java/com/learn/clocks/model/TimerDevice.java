package com.learn.clocks.model;

public class TimerDevice implements DeviceController {
    private long remaining;
    private boolean running = false;

    public TimerDevice(long seconds) {
        this.remaining = seconds;
    }

    @Override
    public void onTick(long timeSeconds) {
        if (running && remaining > 0) {
            remaining--;
        }
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public String getTitle() {
        return "Таймер";
    }

    @Override
    public String getDisplayText() {
        return remaining > 0 ? remaining + " сек" : "Завершено!";
    }
}