package com.learn.turnup.repositories;

import com.learn.turnup.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
