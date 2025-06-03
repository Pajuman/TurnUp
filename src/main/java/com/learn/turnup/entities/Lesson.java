package com.learn.turnup.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity(
        name = "Lesson"
)
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Lesson {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    @Column(
            name = "lesson_name",
            nullable = false,
            unique = false,
            columnDefinition = "TEXT"
    )
    private String lessonName;

    @Column(
            name = "description",
            nullable = false,
            unique = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "shared",
            nullable = false,
            unique = false,
            columnDefinition = "boolean default false"
    )
    private Boolean shared;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Word> words;
}
