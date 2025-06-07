package com.learn.turnup.services;

import com.learn.turnup.dto.AppUserDTO;
import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.entities.AppUser;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.mappers.LessonMapper;
import com.learn.turnup.repositories.AppUserRepository;
import com.learn.turnup.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;

    public UUID createUser(AppUserDTO appUserDTO) {
        AppUser appUser = new AppUser();
        appUser.setAppUserName(appUserDTO.getAppUserName());
        appUser.setPasswordHash(appUserDTO.getPasswordHash());

        return appUserRepository.save(appUser).getId();
    }

    public ResponseEntity<Void> deleteUser(UUID xUserId) {
        appUserRepository.findById(xUserId).ifPresent(appUserRepository::delete);

        return null;
    }

    public List<LessonDTO> getLessonsOfLoggedInUser(UUID xUserId) {
        List<Lesson> userLessons = lessonRepository.findAllByAppUserId(xUserId).orElse(Collections.emptyList());
        return userLessons.stream().map(lessonMapper::toDto).toList();
    }

    public UUID loginUser(AppUserDTO appUserDTO) {
        AppUser appUser = appUserRepository.findByAppUserNameAndPasswordHash(appUserDTO.getAppUserName(), appUserDTO.getPasswordHash());
        return appUser.getId();
    }

    public ResponseEntity<Void> updateUser(UUID xUserId, AppUserDTO appUserDTO) {
        AppUser appUser = appUserRepository.findById(xUserId).orElse(null);
        if (appUser != null) {
            appUser.setAppUserName(appUserDTO.getAppUserName());
            appUser.setPasswordHash(appUserDTO.getPasswordHash());
            appUserRepository.save(appUser);
        }
        return null;
    }
}
