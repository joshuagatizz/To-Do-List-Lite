package com.example.todolistlite.service.impl;

import com.example.todolistlite.entity.Activity;
import com.example.todolistlite.model.CreateActivityRequest;
import com.example.todolistlite.model.UpdateActivityRequest;
import com.example.todolistlite.repository.ActivityRepository;
import com.example.todolistlite.service.ActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public Activity createActivity(CreateActivityRequest request) {
        Activity activity = Activity.builder()
                .isCompleted(false)
                .build();
        BeanUtils.copyProperties(request, activity);
        return activityRepository.save(activity);
    }

    @Override
    public Iterable<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> getAllActivitiesByName(String content) {
        return activityRepository.findActivitiesByContentLike(content);
    }

    @Override
    public Activity getActivityById(String id) {
        return activityRepository.findById(id).get();
    }

    @Override
    public Activity editActivityById(String id, UpdateActivityRequest request) {
        Activity activity = activityRepository.findById(id).get();
        BeanUtils.copyProperties(request, activity);
        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivityById(String id) {
        activityRepository.deleteById(id);
    }
    @Override
    public void deleteAllActivities() {
        activityRepository.deleteAll();
    }
}
