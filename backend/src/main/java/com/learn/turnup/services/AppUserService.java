package com.learn.turnup.services;

import com.learn.turnup.dto.AppUserDTO;
import com.learn.turnup.dto.DeleteUserRequest;
import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.dto.UpdateUserRequest;
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
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.learn.turnup.exceptions.DefaultUser.denyForDefaultUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    public List<LessonDTO> getLessonsOfLoggedInUser(UUID xUserId) {
        //400, 401
        validationService.checkAppUserId(xUserId);

      List<Lesson> userLessons = lessonRepository.findAllByAppUserId(xUserId).orElse(Collections.emptyList());
      List<Lesson> sharedLessons = lessonRepository.findAllBySharedIsTrueAndAppUserIdNot(xUserId).orElse(Collections.emptyList());

      List<LessonDTO> alteredSharedLessonDTOs = sharedLessons.stream()
        .map(lesson -> {
          LessonDTO dto = lessonMapper.toDto(lesson);
          dto.setScore(-1000);
          return dto;
        })
        .toList();

      List<LessonDTO> userLessonDTOs = userLessons.stream()
        .map(lessonMapper::toDto)
        .toList();

      List<LessonDTO> result = new ArrayList<>(userLessonDTOs);
      result.addAll(alteredSharedLessonDTOs);

      return result;
    }

    public UUID createUser(AppUserDTO appUserDTO) {
        //409
        if (appUserRepository.existsByAppUserName(appUserDTO.getAppUserName())) {
            throw new EntityExistsException("User already exists");
        }

        String hashedPassword = passwordEncoder.encode(appUserDTO.getPassword());

        AppUser appUser = new AppUser();
        appUser.setAppUserName(appUserDTO.getAppUserName());
        appUser.setPasswordHash(hashedPassword);

        return appUserRepository.save(appUser).getId();
    }

    public UUID loginUser(AppUserDTO appUserDTO) {
        return appUserRepository.findByAppUserName(appUserDTO.getAppUserName())
                .filter(user -> passwordEncoder.matches(appUserDTO.getPassword(), user.getPasswordHash()))
                .map(AppUser::getId)
                //401
                .orElseThrow(() -> new UnauthorizedException("User name or password mismatch"));
    }

    public void updateUser(UUID xUserId, UpdateUserRequest updateUserRequest) {
      denyForDefaultUser(xUserId);

      //400, 401
        validationService.checkAppUserId(xUserId);

        AppUser appUser = appUserRepository.findById(xUserId).orElse(null);
        if(!passwordEncoder.matches(updateUserRequest.getCurrentPassword(), appUser.getPasswordHash())){
            throw new UnauthorizedException("User name or password mismatch");
        }

        AppUser userWithSameName = appUserRepository.findByAppUserName(updateUserRequest.getAppUserName()).orElse(null);

        if(userWithSameName != null && !userWithSameName.getId().equals(xUserId)){
            throw new EntityExistsException("User with same name already exists");
        }

        String hashedPassword = passwordEncoder.encode(updateUserRequest.getPassword());

        appUser.setAppUserName(updateUserRequest.getAppUserName());
        appUser.setPasswordHash(hashedPassword);
        appUserRepository.save(appUser);
    }

    public void deleteUser(UUID xUserId, DeleteUserRequest deleteUserRequest) {
      denyForDefaultUser(xUserId);

      //400, 401
        validationService.checkAppUserId(xUserId);

        AppUser appUser = appUserRepository.findById(xUserId).orElse(null);
        if(!passwordEncoder.matches(deleteUserRequest.getCurrentPassword(), appUser.getPasswordHash())){
            throw new UnauthorizedException("User name or password mismatch");
        }

        appUserRepository.findById(xUserId).ifPresent(appUserRepository::delete);
    }
}
