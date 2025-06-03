package com.learn.turnup.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import java.util.UUID;

@Entity(
        name = "AppUser"
)
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "app_user_name",
            nullable = false,
            unique = true,
            columnDefinition = "TEXT"
    )
    private String appUserName;

    @Column(
            name = "password_hash",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String passwordHash;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Lesson> lessons;
}
