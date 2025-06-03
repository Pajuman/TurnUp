package com.learn.turnup.repositories;

import com.learn.turnup.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
