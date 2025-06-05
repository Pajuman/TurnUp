package com.learn.turnup.services;

import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.dto.NewLessonDTO;
import com.learn.turnup.dto.NewWordDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.entities.AppUser;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import com.learn.turnup.mappers.LessonMapper;
import com.learn.turnup.mappers.WordMapper;
import com.learn.turnup.repositories.AppUserRepository;
import com.learn.turnup.repositories.LessonRepository;
import com.learn.turnup.repositories.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonsService {
    private final LessonRepository lessonRepository;
    private final WordRepository wordRepository;
    private final AppUserRepository appUserRepository;
    private final LessonMapper lessonMapper;
    private final WordMapper wordMapper;


    public ResponseEntity<Void> copySharedLesson(UUID xUserId, UUID lessonId) {
        Lesson copiedLesson = lessonRepository.findById(lessonId).orElse(null);
        AppUser targetUser = appUserRepository.findById(xUserId).orElse(null);
        if(copiedLesson == null || targetUser == null || copiedLesson.getShared().equals(false)) {
            return null;
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

        return null;
    }

    public ResponseEntity<Void> createLesson(UUID xUserId, NewLessonDTO newLessonDTO) {
        AppUser user = appUserRepository.findById(xUserId).orElse(null);
        if(user == null){
            return null;
        }
        Lesson newLesson = lessonMapper.toEntity(newLessonDTO);
        newLesson.setAppUser(user);

        lessonRepository.save(newLesson);

        return null;
    }

    public ResponseEntity<Void> createWords(UUID xUserId, UUID lessonId, List<NewWordDTO> newWordDTOs) {
        AppUser user = appUserRepository.findById(xUserId).orElse(null);
        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);
        if(user == null || lesson == null){
            return null;
        }

        //filters out Words which already exist
        newWordDTOs = newWordDTOs.stream().filter(newWordDTO -> wordRepository.findByQuestionAndAnswer(newWordDTO.getQuestion(), newWordDTO.getAnswer()).isEmpty()).toList();

        List<Word> newWords = newWordDTOs.stream().map(wordMapper::toEntity).peek(word -> word.setLesson(lesson)).toList();

        wordRepository.saveAll(newWords);

        return null;
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
}
