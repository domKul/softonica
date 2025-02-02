package com.softonicabackend.infrastructure.email;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private final List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message, String email) {
        for (Observer observer : observers) {
            observer.update(message, email);
        }
    }
}