package com.learn.turnup.controllers;

import com.learn.turnup.apis.UsersApi;
import com.learn.turnup.dto.AppUserDTO;
import com.learn.turnup.dto.LessonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.annotation.Generated;

import java.util.List;
import java.util.UUID;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-03T14:15:17.222973100+02:00[Europe/Prague]", comments = "Generator version: 7.9.0")
@Controller
@RequestMapping("${openapi.word.base-path:/api}")
public class UsersApiController implements UsersApi {

    @Override
    public ResponseEntity<UUID> createUser(AppUserDTO appUserDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID xUserId) {
        return null;
    }

    @Override
    public ResponseEntity<List<LessonDTO>> getLessonsOfLoggedInUser(UUID xUserId) {
        return null;
    }

    @Override
    public ResponseEntity<UUID> loginUser(AppUserDTO appUserDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateUser(UUID xUserId, AppUserDTO appUserDTO) {
        return null;
    }
}
