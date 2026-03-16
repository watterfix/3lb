package com.learn.clocks.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.*;

public class CentralTimeTicker implements TimeSubject {

    private final List<TimeObserver> observers = new ArrayList<>();
    private final Timeline timeline;
    private long time = 0;

    public CentralTimeTicker() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void tick() {
        time++;
        notifyObservers();
    }

    public void start() { timeline.play(); }
    public void stop() { timeline.stop(); }

    public void setTime(long time) {
        this.time = time;
        notifyObservers();
    }

    public long getTime() { return time; }

    @Override
    public void addObserver(TimeObserver observer) { observers.add(observer); }

    @Override
    public void removeObserver(TimeObserver observer) { observers.remove(observer); }

    private void notifyObservers() {
        observers.forEach(o -> o.onTick(time));
    }
}