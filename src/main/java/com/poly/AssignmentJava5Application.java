package com.poly;

import com.poly.config.StorageProperties;
import com.poly.service.StorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AssignmentJava5Application {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentJava5Application.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}

}
