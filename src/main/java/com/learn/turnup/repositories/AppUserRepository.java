package com.learn.turnup.repositories;

import com.learn.turnup.entities.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {
}
