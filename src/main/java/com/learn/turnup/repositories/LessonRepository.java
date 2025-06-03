package com.learn.turnup.repositories;

import com.learn.turnup.entities.Lesson;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends CrudRepository<Lesson, Integer> {
}
