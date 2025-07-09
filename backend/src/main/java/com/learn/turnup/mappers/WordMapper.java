package com.learn.turnup.mappers;

import com.learn.turnup.dto.NewWordDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.entities.Word;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WordMapper {

    Word toEntity(NewWordDTO newWordDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "question", source = "question")
    @Mapping(target = "answer", source = "answer")
    @Mapping(target = "score", source = "score")
    WordDTO toDto(Word word);

}
