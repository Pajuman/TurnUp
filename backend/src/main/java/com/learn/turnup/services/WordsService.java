package com.learn.turnup.services;

import com.deepl.api.DeepLClient;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.learn.turnup.dto.BatchWordUpdateDTO;
import com.learn.turnup.dto.TranslationDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.dto.WordScoreDTO;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import com.learn.turnup.exceptions.GlobalExceptions.ForbiddenException;
import com.learn.turnup.exceptions.ValidationService;
import com.learn.turnup.repositories.LessonRepository;
import com.learn.turnup.repositories.WordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learn.turnup.exceptions.DefaultUser.denyForDefaultUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordsService {

    private final WordRepository wordRepository;
    private final LessonRepository lessonRepository;
    private final ValidationService validationService;
    private final DeepLClient deeplClient;

    public void updateAndDeleteWords(UUID xUserId, BatchWordUpdateDTO batchWordUpdateDTO) {
      denyForDefaultUser(xUserId);

      List<WordDTO> wordDTOsToUpdate = batchWordUpdateDTO.getUpdatedWords();
        List<UUID> updatedWordIds = batchWordUpdateDTO.getUpdatedWords().stream().map(WordDTO::getId).toList();
        List<Word> wordsToUpdate = wordRepository.findAllById(updatedWordIds);
        List<UUID> deletedWordIds = batchWordUpdateDTO.getDeletedWordIds();
        List<Word> wordsToDelete = wordRepository.findAllById(deletedWordIds);

        //401
        validationService.checkAppUserId(xUserId);

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
    }

    public void updateWordsScores(UUID xUserId, List<WordScoreDTO> wordScoreDTOs){
        wordScoreDTOs.forEach(wordScoreDTO -> {
            Word word = wordRepository.findById(wordScoreDTO.getId()).orElse(null);
            //403
            Lesson wordsLesson = word.getLesson();
            if(wordsLesson != null && !wordsLesson.getAppUser().getId().equals(xUserId) ){
                throw new ForbiddenException("You do not have access to all specified words");
            }

            word.setScore(Byte.parseByte(wordScoreDTO.getScore().toString()));
            wordRepository.save(word);
        });
    }

    public String translateWord(TranslationDTO translationDTO) {
        try{TextResult result =
                deeplClient.translateText(translationDTO.getText(), translationDTO.getSourceLang(), translationDTO.getTargetLang());
        return  "\"" + result.getText() + "\"";}
        catch (DeepLException e){
            System.err.println("DeepL API error: " + e.getMessage());
            throw new RuntimeException("Translation failed: " + e.getMessage(), e);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("Translation request was interrupted", e);
        }
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
