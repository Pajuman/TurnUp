package com.learn.turnup.controllers;

import java.util.UUID;

import com.learn.turnup.apis.LessonsApi;
import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.dto.NewLessonDTO;
import com.learn.turnup.dto.NewWordDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.services.LessonsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import jakarta.annotation.Generated;

@CrossOrigin(origins = "http://localhost:4200")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-03T14:15:17.222973100+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
@Controller
@RequestMapping("${openapi.word.base-path:/api}")
@RequiredArgsConstructor
public class LessonsApiController implements LessonsApi {

    private final LessonsService lessonsService;


    @Override
    public ResponseEntity<List<WordDTO>> getWordsByLessonId(UUID xUserId, UUID lessonId) {
        return ResponseEntity.ok(lessonsService.getWordsByLessonId(xUserId, lessonId));
    }

    @Override
    public ResponseEntity<LessonDTO> copySharedLesson(UUID xUserId, UUID lessonId) {
        return ResponseEntity.ok(lessonsService.copySharedLesson(xUserId, lessonId));
    }

    @Override
    public ResponseEntity<LessonDTO> createLesson(UUID xUserId, NewLessonDTO newLessonDTO) {
        return ResponseEntity.ok(lessonsService.createLesson(xUserId, newLessonDTO));
    }

    @Override
    public ResponseEntity<WordDTO> createWord(UUID xUserId, UUID lessonId, NewWordDTO newWordDTO) {
        return ResponseEntity.ok(lessonsService.createWord(xUserId, lessonId, newWordDTO));
    }

    @Override
    public ResponseEntity<Void> updateLesson(UUID xUserId, LessonDTO lessonDTO) {
        lessonsService.updateLesson(xUserId, lessonDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteLesson(UUID xUserId, UUID lessonId) {
        lessonsService.deleteLesson(xUserId, lessonId);
        return ResponseEntity.noContent().build();
    }
}
