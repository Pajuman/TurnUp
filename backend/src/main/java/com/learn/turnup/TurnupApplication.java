package com.learn.turnup;

import com.deepl.api.DeepLClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TurnupApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurnupApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DeepLClient deeplClient(@Value("${deepl.api.key}") String apiKey) {
		return new DeepLClient(apiKey);
	}
}
