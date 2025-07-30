package com.learn.turnup.controllers;

import com.learn.turnup.apis.UsersApi;
import com.learn.turnup.dto.AppUserDTO;
import com.learn.turnup.dto.LessonDTO;
import com.learn.turnup.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.annotation.Generated;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-03T14:15:17.222973100+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
@Controller
@RequestMapping("${openapi.word.base-path:/api}")
@RequiredArgsConstructor
public class UsersApiController implements UsersApi {

    private final AppUserService appUserService;

    @Override
    public ResponseEntity<List<LessonDTO>> getLessonsOfLoggedInUser(UUID xUserId) {
        return ResponseEntity.ok(appUserService.getLessonsOfLoggedInUser(xUserId));
    }

    @Override
    public ResponseEntity<UUID> loginUser(AppUserDTO appUserDTO) {
      return ResponseEntity.ok(appUserService.loginUser(appUserDTO));
    }

    @Override
    public ResponseEntity<UUID> createUser(AppUserDTO appUserDTO) {
        return ResponseEntity.ok(appUserService.createUser(appUserDTO));
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID xUserId, AppUserDTO appUserDTO) {
        appUserService.updateUser(xUserId, appUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID xUserId) {
        appUserService.deleteUser(xUserId);
        return ResponseEntity.noContent().build();
    }
}
