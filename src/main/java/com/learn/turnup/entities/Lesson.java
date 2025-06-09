package com.learn.turnup.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.UUID;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "lesson_name",
            nullable = false,
            unique = false
    )
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[\\p{L} ]+$")
    private String lessonName;

    @Column(
            name = "description",
            nullable = false,
            unique = false
    )
    @Size(min = 3, max = 60)
    @Pattern(regexp = "^[\\p{L} ]+$")
    private String description;

    @Column(
            name = "shared",
            nullable = false,
            unique = false
    )
    private Boolean shared;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<Word> words;
}
