package com.learn.turnup.repositories;

import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    Optional<Word> findByQuestionAndAnswer(String question, String answer);
    Optional<List<Word>> findAllByLesson(Lesson lesson);
}
