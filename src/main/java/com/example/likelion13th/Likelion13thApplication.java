package com.example.likelion13th;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Enables JPA Auditing to automatically populate createdAt and updatedAt fields
public class Likelion13thApplication {

	public static void main(String[] args) {
		SpringApplication.run(Likelion13thApplication.class, args);
	}

}