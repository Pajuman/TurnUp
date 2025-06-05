package com.learn.turnup.services;

import com.learn.turnup.dto.BatchWordUpdateDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import com.learn.turnup.repositories.LessonRepository;
import com.learn.turnup.repositories.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordsService {
    private final WordRepository wordRepository;
    private final LessonRepository lessonRepository;

    public ResponseEntity<Void> updateWords(UUID xUserId, BatchWordUpdateDTO batchWordUpdateDTO) {
        List<Lesson> userLessons = lessonRepository.findAllByAppUserId(xUserId).orElse(Collections.emptyList());
        List<UUID> userLessonsIds = userLessons.stream().map(Lesson::getId).toList();

        deleteWords(userLessonsIds, batchWordUpdateDTO);
        updateWords(userLessonsIds, batchWordUpdateDTO);

        return null;
    }

    private void deleteWords(List<UUID> userLessonsIds, BatchWordUpdateDTO batchWordUpdateDTO){
        List<UUID> deletedWordIds = batchWordUpdateDTO.getDeletedWordIds();
        List<Word> wordsToDelete = wordRepository.findAllById(deletedWordIds);

        wordsToDelete = wordsToDelete.stream().filter(word -> userLessonsIds.contains(word.getLesson().getId()) ).toList();

        wordRepository.deleteAll(wordsToDelete);
    }

    private void updateWords(List<UUID> userLessonsIds, BatchWordUpdateDTO batchWordUpdateDTO) {
        List<WordDTO> updatedWordDTOs = batchWordUpdateDTO.getUpdatedWords();
        List<UUID> updatedWordIds = batchWordUpdateDTO.getUpdatedWords().stream().map(WordDTO::getId).toList();
        List<Word> wordsToUpdate = wordRepository.findAllById(updatedWordIds);

        wordsToUpdate = wordsToUpdate.stream().filter(word -> userLessonsIds.contains(word.getLesson().getId()) ).toList();

        Map<UUID, WordDTO> mappedUpdatedWordDTOs = updatedWordDTOs.stream().collect(Collectors.toMap(WordDTO::getId, dto -> dto));

        for (Word word : wordsToUpdate) {
            WordDTO dto = mappedUpdatedWordDTOs.get(word.getId());
            word.setQuestion(dto.getQuestion());
            word.setAnswer(dto.getAnswer());
        }

        wordRepository.saveAll(wordsToUpdate);
    }
}
