package com.learn.turnup.mappers;

import com.learn.turnup.dto.NewWordDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.entities.Word;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-21T09:02:59+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class WordMapperImpl implements WordMapper {

    @Override
    public Word toEntity(NewWordDTO newWordDto) {
        if ( newWordDto == null ) {
            return null;
        }

        Word word = new Word();

        word.setQuestion( newWordDto.getQuestion() );
        word.setAnswer( newWordDto.getAnswer() );

        return word;
    }

    @Override
    public WordDTO toDto(Word word) {
        if ( word == null ) {
            return null;
        }

        WordDTO wordDTO = new WordDTO();

        wordDTO.setId( word.getId() );
        wordDTO.setQuestion( word.getQuestion() );
        wordDTO.setAnswer( word.getAnswer() );
        if ( word.getScore() != null ) {
            wordDTO.setScore( word.getScore().intValue() );
        }

        return wordDTO;
    }
}
