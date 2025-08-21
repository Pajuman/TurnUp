package com.learn.turnup.services;

import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.dto.NewLessonDTO;
import com.learn.turnup.dto.NewWordDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.entities.AppUser;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import com.learn.turnup.exceptions.GlobalExceptions.ForbiddenException;
import com.learn.turnup.exceptions.ValidationService;
import com.learn.turnup.mappers.LessonMapper;
import com.learn.turnup.mappers.WordMapper;
import com.learn.turnup.repositories.AppUserRepository;
import com.learn.turnup.repositories.LessonRepository;
import com.learn.turnup.repositories.WordRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.learn.turnup.exceptions.DefaultUser.denyForDefaultUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonsService {
    private final LessonRepository lessonRepository;
    private final WordRepository wordRepository;
    private final AppUserRepository appUserRepository;
    private final LessonMapper lessonMapper;
    private final WordMapper wordMapper;
    private final ValidationService validationService;


    public List<WordDTO> getWordsByLessonId(UUID xUserId, UUID lessonId) {
        //401
        validationService.checkAppUserId(xUserId);
        //404
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new EntityNotFoundException("Lesson not found"));
        //403
        if(!lesson.getAppUser().getId().equals(xUserId) && lesson.getShared() == false){
            throw new ForbiddenException("You are not allowed to access this lesson");
        }
        List<Word> words = wordRepository.findAllByLesson(lesson).orElse(Collections.emptyList());

        return words.stream().map(wordMapper::toDto).toList();
    }

    public LessonDTO copySharedLesson(UUID xUserId, UUID lessonId) {
      denyForDefaultUser(xUserId);

      //401
        validationService.checkAppUserId(xUserId);
        //404
        Lesson copiedLesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new EntityNotFoundException("Lesson not found"));
        //403
        if(copiedLesson.getShared().equals(false)) {
            throw new ForbiddenException("The lesson is not for sharing");
        }
        //409
        if(lessonRepository.existsByLessonNameAndAppUser_Id(copiedLesson.getLessonName(), xUserId)) {
            throw new EntityExistsException("Lesson name already exists");
        }

        AppUser targetUser = appUserRepository.findById(xUserId).orElse(null);
        List<Word> copiedWords = wordRepository.findAllByLesson(copiedLesson).orElse(Collections.emptyList());

        Lesson newLesson = new Lesson();
        newLesson.setLessonName(copiedLesson.getLessonName());
        newLesson.setDescription(copiedLesson.getDescription());
        newLesson.setShared(false);
        newLesson.setAppUser(targetUser);
        newLesson.setLanguage((copiedLesson.getLanguage()));

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

        Lesson newLessonWithWordCount = lessonRepository.findById(newLesson.getId()).orElse(finalNewLesson);
        return lessonMapper.toDto(newLessonWithWordCount);
    }

    public LessonDTO createLesson(UUID xUserId, NewLessonDTO newLessonDTO) {
      denyForDefaultUser(xUserId);

      //401
        validationService.checkAppUserId(xUserId);
        //409
        if(lessonRepository.existsByLessonNameAndAppUser_Id(newLessonDTO.getLessonName(), xUserId)) {
            throw new EntityExistsException("Lesson name already exists");
        }

        AppUser user = appUserRepository.findById(xUserId).orElse(null);

        Lesson newLesson = lessonMapper.toEntity(newLessonDTO);
        newLesson.setAppUser(user);

        newLesson = lessonRepository.save(newLesson);

        return lessonMapper.toDto(newLesson);
    }

    public WordDTO createWord(UUID xUserId, UUID lessonId, NewWordDTO newWordDTO) {
      denyForDefaultUser(xUserId);

        //401
        validationService.checkAppUserId(xUserId);

        //404
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new EntityNotFoundException("Lesson not found"));
        //403
        if(!lesson.getAppUser().getId().equals(xUserId)) {
            throw new ForbiddenException("You are not allowed to access this lesson");
        }

        Word newWord = wordRepository.findByQuestionAndAnswerAndLesson(newWordDTO.getQuestion(), newWordDTO.getAnswer(), lesson).orElse(wordMapper.toEntity(newWordDTO));
        newWord.setLesson(lesson);

        newWord = wordRepository.save(newWord);

        return wordMapper.toDto(newWord);
    }

    public void updateLesson(UUID xUserId, LessonDTO lessonDTO) {
      denyForDefaultUser(xUserId);

      //401
        validationService.checkAppUserId(xUserId);
        //404
        Lesson lesson = lessonRepository.findById(lessonDTO.getId()).orElseThrow(() ->
                new EntityNotFoundException("Lesson not found"));
        //403
        if(!lesson.getAppUser().getId().equals(xUserId)){
            throw new ForbiddenException("You are not allowed to access this lesson");
        }
        //409
        Optional<UUID> lessonWithSameNameId = lessonRepository
                .findByLessonNameAndAppUser_Id(lessonDTO.getLessonName(), xUserId)
                .map(Lesson::getId);
        if (lessonWithSameNameId.isPresent() && !lessonWithSameNameId.get().equals(lessonDTO.getId())) {
            throw new EntityExistsException("Lesson name already exists");
        }

        lesson.setLessonName(lessonDTO.getLessonName());
        lesson.setDescription(lessonDTO.getDescription());
        lesson.setShared(lessonDTO.getShared());

        lessonRepository.save(lesson);
    }

    public void deleteLesson(UUID xUserId, UUID lessonId) {
      denyForDefaultUser(xUserId);

      //401
        validationService.checkAppUserId(xUserId);
        //403
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if(lesson != null && !lesson.getAppUser().getId().equals(xUserId)){
            throw new ForbiddenException("You are not allowed to access this lesson");
        }

        if(lesson != null) {
            lessonRepository.delete(lesson);
        }
    }
}
