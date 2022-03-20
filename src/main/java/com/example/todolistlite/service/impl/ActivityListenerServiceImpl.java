package com.example.todolistlite.service.impl;

import com.example.todolistlite.model.CreateActivityRequest;
import com.example.todolistlite.service.ActivityListenerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ActivityListenerServiceImpl implements ActivityListenerService {
    @Override
    @KafkaListener(topics = "todos", groupId = "activity_group", containerFactory = "kafkaListenerContainerFactory")
    public void onEventConsumed(String msg) {
        System.out.println("New activity created : " + msg);
    }
}
