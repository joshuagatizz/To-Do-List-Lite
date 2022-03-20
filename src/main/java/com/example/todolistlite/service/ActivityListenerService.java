package com.example.todolistlite.service;

import com.example.todolistlite.model.CreateActivityRequest;

public interface ActivityListenerService {
    void onEventConsumed(String msg);
}
