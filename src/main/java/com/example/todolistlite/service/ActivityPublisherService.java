package com.example.todolistlite.service;

import com.example.todolistlite.model.CreateActivityRequest;

public interface ActivityPublisherService {
    void sendMessage(CreateActivityRequest createActivityRequest);
}
