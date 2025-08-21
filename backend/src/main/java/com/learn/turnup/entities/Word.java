package com.learn.turnup.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity(
        name = "Word"
)
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "question",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Size(min = 1, max = 40)
    private String question;

    @Column(
            name = "answer",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Size(min = 1, max = 40)
    private String answer;

    @Column(
            name = "score",
            nullable = false,
           columnDefinition = "integer default 0"
    )
    private Byte score = 0;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
