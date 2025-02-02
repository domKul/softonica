package com.softonicabackend.infrastructure.email;

public interface Observer {
    void update(String message, String email);
}