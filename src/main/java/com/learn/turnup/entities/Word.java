package com.learn.turnup.entities;

import jakarta.persistence.*;
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
            unique = false,
            columnDefinition = "TEXT"
    )
    private String question;
    @Column(
            name = "answer",
            nullable = false,
            unique = false,
            columnDefinition = "TEXT"
    )
    private String answer;
    @Column(
            name = "score",
            nullable = true,
            unique = false,
           columnDefinition = "integer default 0"
    )
    private Byte score;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
}
