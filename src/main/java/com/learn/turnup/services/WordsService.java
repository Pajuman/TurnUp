package com.learn.turnup.services;

import com.learn.turnup.dto.BatchWordUpdateDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import com.learn.turnup.exceptions.GlobalExceptions.ForbiddenException;
import com.learn.turnup.exceptions.GlobalExceptions.UnauthorizedException;
import com.learn.turnup.repositories.AppUserRepository;
import com.learn.turnup.repositories.LessonRepository;
import com.learn.turnup.repositories.WordRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordsService {
    private final WordRepository wordRepository;
    private final LessonRepository lessonRepository;
    private final AppUserRepository appUserRepository;
    private final Validator validator;

    public ResponseEntity<Void> updateWords(UUID xUserId, BatchWordUpdateDTO batchWordUpdateDTO) {
        List<WordDTO> wordDTOsToUpdate = batchWordUpdateDTO.getUpdatedWords();
        List<UUID> updatedWordIds = batchWordUpdateDTO.getUpdatedWords().stream().map(WordDTO::getId).toList();
        List<Word> wordsToUpdate = wordRepository.findAllById(updatedWordIds);
        List<UUID> deletedWordIds = batchWordUpdateDTO.getDeletedWordIds();
        List<Word> wordsToDelete = wordRepository.findAllById(deletedWordIds);

        //400
        wordDTOsToUpdate.forEach(wordDTO -> {
            Set<ConstraintViolation<WordDTO>> violations = validator.validate(wordDTO);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        });

        //401
        if(!appUserRepository.existsById(xUserId)){
            throw new UnauthorizedException("User id missing or invalid");
        }

        //403
        List<Lesson> userLessons = lessonRepository.findAllByAppUserId(xUserId).orElse(Collections.emptyList());
        List<UUID> userLessonsIds = userLessons.stream().map(Lesson::getId).toList();

        Stream.concat(wordsToDelete.stream(), wordsToUpdate.stream())
                .forEach(word -> {
                    if (!userLessonsIds.contains(word.getLesson().getId())) {
                        throw new ForbiddenException("You do not have access to all specified words");
                    }
                });

        wordRepository.deleteAll(wordsToDelete);
        updateWords(wordDTOsToUpdate, wordsToUpdate);

        return null;
    }

    private void updateWords(List<WordDTO> wordDTOsToUpdate, List<Word> wordsToUpdate) {

        Map<UUID, WordDTO> mappedUpdatedWordDTOs = wordDTOsToUpdate.stream().collect(Collectors.toMap(WordDTO::getId, dto -> dto));

        for (Word word : wordsToUpdate) {
            WordDTO dto = mappedUpdatedWordDTOs.get(word.getId());
            word.setQuestion(dto.getQuestion());
            word.setAnswer(dto.getAnswer());
        }

        wordRepository.saveAll(wordsToUpdate);
    }
}
