package com.learn.turnup.services;

import com.learn.turnup.dto.AppUserDTO;
import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.entities.AppUser;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.exceptions.GlobalExceptions.UnauthorizedException;
import com.learn.turnup.exceptions.ValidationService;
import com.learn.turnup.mappers.LessonMapper;
import com.learn.turnup.repositories.AppUserRepository;
import com.learn.turnup.repositories.LessonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final ValidationService validationService;

    public List<LessonDTO> getLessonsOfLoggedInUser(UUID xUserId) {
        //400, 401
        validationService.checkAppUserId(xUserId);

        List<Lesson> userLessons = lessonRepository.findAllByAppUserId(xUserId).orElse(Collections.emptyList());
        return  userLessons.stream().map(lessonMapper::toDto).toList();
    }

    public UUID createUser(AppUserDTO appUserDTO) {
        //409
        if (appUserRepository.existsByAppUserName(appUserDTO.getAppUserName())) {
            throw new EntityExistsException("User already exists");
        }

        AppUser appUser = new AppUser();
        appUser.setAppUserName(appUserDTO.getAppUserName());
        appUser.setPasswordHash(appUserDTO.getPasswordHash());

        return appUserRepository.save(appUser).getId();
    }

    public UUID loginUser(AppUserDTO appUserDTO) {
        //401
        AppUser appUser = appUserRepository.findByAppUserNameAndPasswordHash(appUserDTO.getAppUserName(), appUserDTO.getPasswordHash()).orElseThrow(
                () -> new UnauthorizedException("User name or password mismatch")
        );

        return appUser.getId();
    }

    public void updateUser(UUID xUserId, AppUserDTO appUserDTO) {
        //400, 401
        validationService.checkAppUserId(xUserId);

        AppUser appUser = appUserRepository.findById(xUserId).orElse(null);

        AppUser userWithSameName = appUserRepository.findByAppUserName(appUserDTO.getAppUserName()).orElse(null);

        if(userWithSameName != null && !userWithSameName.getId().equals(xUserId)){
            throw new EntityExistsException("User with same name already exists");
        }

        appUser.setAppUserName(appUserDTO.getAppUserName());
        appUser.setPasswordHash(appUserDTO.getPasswordHash());
        appUserRepository.save(appUser);
    }

    public void deleteUser(UUID xUserId) {
        //400, 401
        validationService.checkAppUserId(xUserId);

        appUserRepository.findById(xUserId).ifPresent(appUserRepository::delete);
    }
}
