package com.learn.turnup.mappers;

import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.dto.NewLessonDTO;
import com.learn.turnup.entities.Lesson;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-06T14:10:50+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class LessonMapperImpl implements LessonMapper {

    @Override
    public Lesson toEntity(NewLessonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Lesson lesson = new Lesson();

        lesson.setLessonName( dto.getLessonName() );
        lesson.setDescription( dto.getDescription() );
        lesson.setShared( dto.getShared() );
        lesson.setLanguage( dto.getLanguage() );

        return lesson;
    }

    @Override
    public LessonDTO toDto(Lesson dto) {
        if ( dto == null ) {
            return null;
        }

        LessonDTO lessonDTO = new LessonDTO();

        lessonDTO.setId( dto.getId() );
        lessonDTO.setLessonName( dto.getLessonName() );
        lessonDTO.setDescription( dto.getDescription() );
        lessonDTO.setShared( dto.getShared() );
        lessonDTO.setLanguage( dto.getLanguage() );
        lessonDTO.setScore( (int) dto.getScore() );
        lessonDTO.setWordCount( dto.getWordCount() );

        return lessonDTO;
    }
}
