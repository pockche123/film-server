package com.parjalRai.films;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FilmsApplication {

	public static void main(String[] args) {


		SpringApplication.run(FilmsApplication.class, args);
		System.out.println("THIS IS THE ENVVVVVVVVVVVVVVVVV "+ System.getenv("MONGO_DATABASE"));

	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer customizer()
	{
		return builder -> builder.serializerByType(ObjectId.class,new ToStringSerializer());
	}

}
