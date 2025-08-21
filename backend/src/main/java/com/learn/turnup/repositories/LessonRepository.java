package com.learn.turnup.repositories;

import com.learn.turnup.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    Optional<List<Lesson>> findAllByAppUserId(UUID xUserId);
  Optional<List<Lesson>> findAllBySharedIsTrueAndAppUserIdNot(UUID xUserId);;
  Boolean existsByLessonNameAndAppUser_Id(String lessonName, UUID xUserId);
    Optional<Lesson> findByLessonNameAndAppUser_Id(String lessonName, UUID xUserId);




}
