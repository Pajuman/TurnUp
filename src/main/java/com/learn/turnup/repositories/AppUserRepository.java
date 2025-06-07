package com.learn.turnup.repositories;

import com.learn.turnup.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByAppUserNameAndPasswordHash(String username, String password);
}
