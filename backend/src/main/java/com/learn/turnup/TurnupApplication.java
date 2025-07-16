package com.learn.turnup;

import com.learn.turnup.entities.AppUser;
import com.learn.turnup.entities.Lesson;
import com.learn.turnup.entities.Word;
import com.learn.turnup.repositories.AppUserRepository;
import com.learn.turnup.repositories.LessonRepository;
import com.learn.turnup.repositories.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class TurnupApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurnupApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
