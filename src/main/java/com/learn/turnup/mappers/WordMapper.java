package com.learn.turnup.mappers;

import com.learn.turnup.dto.NewWordDTO;
import com.learn.turnup.dto.WordDTO;
import com.learn.turnup.entities.Word;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordMapper {

    Word toEntity(NewWordDTO newWordDto);

    WordDTO toDto(Word word);

}
