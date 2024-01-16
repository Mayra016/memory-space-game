package com.Guess.Word;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.Guess.Word.Repositories")
@ComponentScan(basePackages = "com.Guess.Word")
@EntityScan(basePackages = "com.Guess.Word.Entities")
@EnableScheduling
public class GuessTheWordApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuessTheWordApplication.class, args);
	}

}
