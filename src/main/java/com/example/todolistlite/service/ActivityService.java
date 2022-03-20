package com.example.todolistlite.service;

import com.example.todolistlite.entity.Activity;
import com.example.todolistlite.model.CreateActivityRequest;
import com.example.todolistlite.model.UpdateActivityRequest;

import java.util.List;

public interface ActivityService {
    Activity createActivity(CreateActivityRequest request);
    Iterable<Activity> getAllActivities();
    List<Activity> getAllActivitiesByName(String content);
    Activity getActivityById(String id);
    Activity editActivityById(String id, UpdateActivityRequest request);
    void deleteActivityById(String id);
    void deleteAllActivities();
}
