package com.learn.turnup.controllers;

import com.learn.turnup.apis.WordsApi;
import com.learn.turnup.dto.BatchWordUpdateDTO;
import com.learn.turnup.dto.WordScoreDTO;
import com.learn.turnup.services.WordsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.annotation.Generated;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-03T14:15:17.222973100+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
@Controller
@RequestMapping("${openapi.word.base-path:/api}")
@RequiredArgsConstructor
public class WordsApiController implements WordsApi {

    private final WordsService wordsService;

    @Override
    public ResponseEntity<Void> updateWords(UUID xUserId, BatchWordUpdateDTO batchWordUpdateDTO) {
        wordsService.updateAndDeleteWords(xUserId, batchWordUpdateDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateWordsScores(UUID xUserId, List<@Valid WordScoreDTO> wordScoreDTO) {
        wordsService.updateWordsScores(xUserId, wordScoreDTO);
        return ResponseEntity.noContent().build();
    }
}
