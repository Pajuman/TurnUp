package com.learn.turnup.repositories;

import com.learn.turnup.entities.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WordRepository extends CrudRepository<Word, Long> {
    Optional<Word> findByQuestionAndAnswer(String question, String answer);
}
