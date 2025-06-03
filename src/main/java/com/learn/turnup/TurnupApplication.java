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

import java.util.List;

@SpringBootApplication
public class TurnupApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurnupApplication.class, args);
	}
/*
	@Bean
	CommandLineRunner loadLessons(AppUserRepository userRepository,
								  LessonRepository lessonRepository,
								  WordRepository wordRepository) {
		return args -> {
			if (lessonRepository.count() == 0 && wordRepository.count() == 0) {
				List<AppUser> users = userRepository.findAll();
				if (users.size() < 3) {
					System.out.println("Not enough users to assign lessons.");
					return;
				}

				AppUser alice = users.get(0);
				AppUser bob = users.get(1);
				AppUser charlie = users.get(2);

				List<Lesson> lessons = lessonRepository.saveAll(List.of(
						createLesson("Basics", "Introductory lesson", false, alice),
						createLesson("Verbs", "Common verbs", true, alice),
						createLesson("Food", "Vocabulary about food", false, bob),
						createLesson("Travel", "Travel-related phrases", true, charlie),
						createLesson("Numbers", "Counting and numbers", false, bob)
				));

				// Generate 4 words per lesson (5 lessons × 4 = 20)
				int counter = 1;
				for (Lesson lesson : lessons) {
					for (int i = 0; i < 4; i++) {
						Word word = new Word();
						word.setQuestion("Question " + counter);
						word.setAnswer("Answer " + counter);
						word.setScore((byte) 0);
						word.setLesson(lesson);
						wordRepository.save(word);
						counter++;
					}
				}
			}
		};
	}

	private Lesson createLesson(String name, String desc, boolean shared, AppUser user) {
		Lesson lesson = new Lesson();
		lesson.setLessonName(name);
		lesson.setDescription(desc);
		lesson.setShared(shared);
		lesson.setAppUser(user);
		return lesson;
	}
*/
}
