package com.example.android_lab_1.service;

public interface Observable {
    void registerObserver(Observer o);
    void removeObserver();
    void notifyObservers(String message);
}
