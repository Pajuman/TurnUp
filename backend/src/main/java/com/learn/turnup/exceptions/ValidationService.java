package com.learn.turnup.exceptions;

import com.learn.turnup.exceptions.GlobalExceptions.UnauthorizedException;
import com.learn.turnup.repositories.AppUserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationService  {
    private final Validator validator;
    private final AppUserRepository appUserRepository;

    public void checkAppUserId(UUID xUserId) {
        validateAppUserId(xUserId);
        checkAppUserExistence(xUserId);
    }
//400
    private void validateAppUserId(UUID xUserId){
        Set<ConstraintViolation<UUID>> violations = validator.validate(xUserId);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Invalid appUserId", violations);
        }
    }
//401
    private void checkAppUserExistence(UUID xUserId){
        if(!appUserRepository.existsById(xUserId)){
            throw new UnauthorizedException("User id missing or invalid");
        }
    }
}