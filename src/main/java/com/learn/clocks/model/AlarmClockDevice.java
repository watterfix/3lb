package com.learn.clocks.model;

public class AlarmClockDevice implements DeviceController {
    private long alarmTime;
    private boolean triggered = false;
    private boolean enabled = true; // по умолчанию включен

    public AlarmClockDevice(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Override
    public void onTick(long timeSeconds) {
        if (enabled && !triggered && timeSeconds >= alarmTime) {
            triggered = true;
        }
    }

    @Override
    public void start() {
        enabled = true;
    }

    @Override
    public void stop() {
        enabled = false;
    }

    @Override
    public String getTitle() {
        return "Будильник";
    }

    @Override
    public String getDisplayText() {
        if (!enabled) {
            return "Выключен | В " + alarmTime + " сек";
        } else if (triggered) {
            return "🔔 СРАБОТАЛ!";
        } else {
            return "Включен | В " + alarmTime + " сек";
        }
    }

    // Геттер для статуса включен/выключен
    public boolean isEnabled() {
        return enabled;
    }
}