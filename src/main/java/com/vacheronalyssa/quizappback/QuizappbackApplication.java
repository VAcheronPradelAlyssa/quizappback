package com.vacheronalyssa.quizappback;

import com.vacheronalyssa.quizappback.entity.Question;
import com.vacheronalyssa.quizappback.entity.Quiz;
import com.vacheronalyssa.quizappback.repository.QuizRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class QuizappbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizappbackApplication.class, args);
	}

	@Bean
	CommandLineRunner seedDefaultQuizzes(QuizRepository quizRepository) {
		return args -> {
			if (quizRepository.count() > 0) {
				return;
			}

			Quiz javaBasics = new Quiz();
			javaBasics.setTitle("Java Basics");
			javaBasics.setQuestions(List.of(
				question(
					"What is Java?",
					List.of("Programming language", "Coffee", "Island", "Framework"),
					"Programming language"
				),
				question(
					"Which keyword is used to inherit a class in Java?",
					List.of("implements", "extends", "inherits", "super"),
					"extends"
				),
				question(
					"Which method is the entry point of a Java application?",
					List.of("start()", "main()", "run()", "init()"),
					"main()"
				)
			));

			Quiz springBoot = new Quiz();
			springBoot.setTitle("Spring Boot Basics");
			springBoot.setQuestions(List.of(
				question(
					"What does Spring Boot mainly help with?",
					List.of("Manual JDBC only", "Rapid application setup", "CSS styling", "Mobile development"),
					"Rapid application setup"
				),
				question(
					"Which annotation marks the main Spring Boot application class?",
					List.of("@Service", "@Repository", "@SpringBootApplication", "@Entity"),
					"@SpringBootApplication"
				),
				question(
					"Which embedded server is commonly used by Spring Boot by default?",
					List.of("Tomcat", "Apache HTTPD", "Nginx", "IIS"),
					"Tomcat"
				)
			));

			Quiz webFundamentals = new Quiz();
			webFundamentals.setTitle("Web Fundamentals");
			webFundamentals.setQuestions(List.of(
				question(
					"What does HTTP stand for?",
					List.of("HyperText Transfer Protocol", "High Transfer Text Program", "Home Tool Transfer Protocol", "Hyper Transfer Text Process"),
					"HyperText Transfer Protocol"
				),
				question(
					"Which HTTP method is typically used to read data?",
					List.of("GET", "POST", "PUT", "DELETE"),
					"GET"
				),
				question(
					"What does CORS control?",
					List.of("Database joins", "Cross-origin requests", "Java memory", "HTML rendering"),
					"Cross-origin requests"
				)
			));

			quizRepository.saveAll(List.of(javaBasics, springBoot, webFundamentals));
		};
	}

	private Question question(String text, List<String> options, String answer) {
		Question question = new Question();
		question.setText(text);
		question.setOptions(options);
		question.setAnswer(answer);
		return question;
	}

}
