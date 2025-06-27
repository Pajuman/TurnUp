package com.learn.turnup.mappers;

import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.dto.NewLessonDTO;
import com.learn.turnup.entities.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    Lesson toEntity(NewLessonDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "lessonName", target = "lessonName"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "shared", target = "shared")
    })
    LessonDTO toDto(Lesson dto);
}
