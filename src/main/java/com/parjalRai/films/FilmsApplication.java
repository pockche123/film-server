package com.parjalRai.films;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmsApplication.class, args);
		System.out.println("THIS IS THE ENVVVVVVVVVVVVVVVVV "+ System.getenv("MONGO_DATABASE"));

	}

}
