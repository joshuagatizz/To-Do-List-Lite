package com.example.todolistlite.repository;

import com.example.todolistlite.entity.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    List<Activity> findActivitiesByContentLike(String content);
}
