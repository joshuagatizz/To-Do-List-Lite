package com.example.todolistlite.service.impl;

import com.example.todolistlite.service.ActivityPublisherService;
import com.example.todolistlite.model.CreateActivityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActivityPublisherServiceImpl implements ActivityPublisherService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(CreateActivityRequest createActivityRequest) {
        kafkaTemplate.send("todos", createActivityRequest.getContent());
    }
}
