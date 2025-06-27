package com.learn.turnup.services;

import com.learn.turnup.dto.*;
import com.learn.turnup.entities.AppUser;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import com.learn.turnup.exceptions.GlobalExceptions.ForbiddenException;
import com.learn.turnup.exceptions.GlobalExceptions.UnauthorizedException;
import com.learn.turnup.mappers.LessonMapper;
import com.learn.turnup.mappers.WordMapper;
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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonsService {
    private final LessonRepository lessonRepository;
    private final WordRepository wordRepository;
    private final AppUserRepository appUserRepository;
    private final LessonMapper lessonMapper;
    private final WordMapper wordMapper;
    private final Validator validator;

    public ResponseEntity<LessonDTO> copySharedLesson(UUID xUserId, UUID lessonId) {
        //400
        Lesson copiedLesson = lessonRepository.findById(lessonId).orElseThrow(() -> new UnauthorizedException("Lesson id not found or invalid")
        );
        AppUser targetUser = getAppUserOrException(xUserId);
        //403
        if(copiedLesson.getShared().equals(false)) {
            throw new ForbiddenException("The lesson is not for sharing");
        }

        List<Word> copiedWords = wordRepository.findAllByLesson(copiedLesson).orElse(Collections.emptyList());

        Lesson newLesson = new Lesson();
        newLesson.setLessonName(copiedLesson.getLessonName());
        newLesson.setDescription(copiedLesson.getDescription());
        newLesson.setShared(false);
        newLesson.setAppUser(targetUser);

        newLesson = lessonRepository.save(newLesson);


        Lesson finalNewLesson = newLesson;
        List<Word> newWords = copiedWords.stream()
                .map(word -> {
                    Word newWord = new Word();
                    newWord.setLesson(finalNewLesson);
                    newWord.setQuestion(word.getQuestion());
                    newWord.setAnswer(word.getAnswer());
                    newWord.setScore((byte) 0);
                    return newWord;
                })
                .toList();

        wordRepository.saveAll(newWords);

        return ResponseEntity.ok(lessonMapper.toDto(finalNewLesson));
    }

    public ResponseEntity<LessonDTO> createLesson(UUID xUserId, NewLessonDTO newLessonDTO) {
        //400
        Set<ConstraintViolation<NewLessonDTO>> violations = validator.validate(newLessonDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        //401
        AppUser user = getAppUserOrException(xUserId);

        Lesson newLesson = lessonMapper.toEntity(newLessonDTO);
        newLesson.setAppUser(user);

        newLesson = lessonRepository.save(newLesson);

        return ResponseEntity.ok(lessonMapper.toDto(newLesson));
    }

    public ResponseEntity<List<WordDTO>> createWords(UUID xUserId, UUID lessonId, List<NewWordDTO> newWordDTOs) {
        AppUser user = getAppUserOrException(xUserId);
        Lesson lesson = getLessonOrException(xUserId, lessonId);

        //filters out Words which already exist
        newWordDTOs = newWordDTOs.stream().filter(newWordDTO -> wordRepository.findByQuestionAndAnswer(newWordDTO.getQuestion(), newWordDTO.getAnswer()).isEmpty()).toList();

        List<Word> newWords = newWordDTOs.stream().map(wordMapper::toEntity).peek(word -> word.setLesson(lesson)).toList();

        newWords = wordRepository.saveAll(newWords);

        return ResponseEntity.ok(newWords.stream().map(wordMapper::toDto).toList());
    }

    public ResponseEntity<Void> deleteLesson(UUID xUserId, UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if(lesson != null && lesson.getAppUser().getId().equals(xUserId)) {
            lessonRepository.delete(lesson);
        }

        return null;
    }

    public ResponseEntity<List<WordDTO>> getWordsByLessonId(UUID xUserId, UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        List<Word> words;
        if(lesson != null && lesson.getAppUser().getId().equals(xUserId)) {
            words = wordRepository.findAllByLesson(lesson).orElse(Collections.emptyList());
        }
        else{
            words = Collections.emptyList();
        }

        List<WordDTO> wordDTOs = words.stream().map(wordMapper::toDto).toList();

        return ResponseEntity.ok(wordDTOs);


    }

    public ResponseEntity<Void> updateLesson(UUID xUserId, LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findById(lessonDTO.getId()).orElse(null);
        if(lesson == null || !lesson.getAppUser().getId().equals(xUserId)) {
            return null;
        }

        lesson.setLessonName(lessonDTO.getLessonName());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setShared(lessonDTO.getShared());

        lessonRepository.save(lesson);

        return null;
    }

    private AppUser getAppUserOrException(UUID xUserId) {
        return appUserRepository.findById(xUserId).orElseThrow(() -> new UnauthorizedException("User not found"));

    }

    private Lesson getLessonOrException(UUID xUserId, UUID lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new UnauthorizedException("Lesson not found"));
        if(lesson.getAppUser().getId().equals(xUserId)) {
            return lesson;
        }
        else{
            throw new ForbiddenException("User has no access to this lesson");
        }
    }
}
