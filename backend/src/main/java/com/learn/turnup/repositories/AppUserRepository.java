package com.learn.turnup.repositories;

import com.learn.turnup.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByAppUserNameAndPasswordHash(String username, String password);
    Boolean existsByAppUserName(String username);
    Optional<AppUser> findByAppUserName(String username);
}
