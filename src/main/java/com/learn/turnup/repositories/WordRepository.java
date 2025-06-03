package com.learn.turnup.repositories;

import com.learn.turnup.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Long> {
    Optional<Word> findByQuestionAndAnswer(String question, String answer);
}
