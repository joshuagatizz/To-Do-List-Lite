package com.example.todolistlite.controller;

import com.example.todolistlite.entity.Activity;
import com.example.todolistlite.model.ActivityResponse;
import com.example.todolistlite.model.CreateActivityRequest;
import com.example.todolistlite.model.UpdateActivityRequest;
import com.example.todolistlite.service.ActivityPublisherService;
import com.example.todolistlite.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityPublisherService activityPublisherService;

    @ApiOperation("add new activity")
    @PostMapping(
            path = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = "find-all-acts", allEntries = true)
    public ActivityResponse createActivity(@RequestBody CreateActivityRequest request) {
        Activity activity = activityService.createActivity(request);
        activityPublisherService.sendMessage(request); // publish to kafka topic
        ActivityResponse activityResponse = ActivityResponse.builder().build();
        BeanUtils.copyProperties(activity, activityResponse);
        return activityResponse;
    }

    @ApiOperation("get all activities")
    @GetMapping(
            path = "/all-act",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "find-all-acts")
    public ArrayList<ActivityResponse> viewAllActivities() {
        ArrayList<ActivityResponse> activityResponseArrayList = new ArrayList<>();
        Iterable<Activity> activities = activityService.getAllActivities();
        for (Activity activity : activities) {
            ActivityResponse activityResponse = toResponse(activity);
            activityResponseArrayList.add(activityResponse);
        }
        return activityResponseArrayList;
    }

    @ApiOperation("get all activities by content")
    @GetMapping(
            path = "/act",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "find-by-content", key = "#content")
    public ArrayList<ActivityResponse> viewActivitiesByContent(@RequestParam String content) {
        ArrayList<ActivityResponse> activityResponseArrayList = new ArrayList<>();
        List<Activity> activities = activityService.getAllActivitiesByName(content);
        for (Activity activity : activities) {
            ActivityResponse activityResponse = toResponse(activity);
            activityResponseArrayList.add(activityResponse);
        }
        return activityResponseArrayList;
    }

    @ApiOperation("get activity by id")
    @GetMapping(
            path = "/act/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(value = "find-by-id", key = "#id")
    public ActivityResponse findActivityById(@PathVariable String id) {
        Activity activity = activityService.getActivityById(id);
        return toResponse(activity);
    }

    @ApiOperation("update activity by id")
    @PutMapping(
            path = "/act/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Caching(evict = {
            @CacheEvict(value = "find-all-acts", allEntries = true),
            @CacheEvict(value = "find-by-content", allEntries = true)
    }, put = {
            @CachePut(value = "find-by-id", key = "#id")
    })
    public ActivityResponse findActivityById(@PathVariable String id, @RequestBody UpdateActivityRequest request) {
        Activity activity = activityService.editActivityById(id, request);
        return toResponse(activity);
    }

    @ApiOperation("delete activity by id")
    @DeleteMapping(
            path = "/act/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Caching(evict = {
            @CacheEvict(value = "find-all-acts", allEntries = true),
            @CacheEvict(value = "find-by-id", key = "#id"),
            @CacheEvict(value = "find-by-content", allEntries = true)
    })
    public Boolean removeActivityById(@PathVariable String id) {
        activityService.deleteActivityById(id);
        return true;
    }

    @ApiOperation("delete all activities")
    @DeleteMapping(
            path = "/act",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Caching(evict = {
            @CacheEvict(value = "find-all-acts", allEntries = true),
            @CacheEvict(value = "find-by-id", allEntries = true),
            @CacheEvict(value = "find-by-content", allEntries = true)
    })
    public Boolean removeAllActivities() {
        activityService.deleteAllActivities();
        return true;
    }

    private ActivityResponse toResponse(Activity activity) {
        ActivityResponse activityResponse = ActivityResponse.builder().build();
        BeanUtils.copyProperties(activity, activityResponse);
        return activityResponse;
    }

}
