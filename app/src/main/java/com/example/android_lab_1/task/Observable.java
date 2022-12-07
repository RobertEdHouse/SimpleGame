package com.example.android_lab_1.task;

public interface Observable {
    void registerObserver(Observer o);
    void notifyObservers(String message);
}
