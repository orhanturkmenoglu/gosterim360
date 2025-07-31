package com.gosterim360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Gosterim360Application {

	public static void main(String[] args) {
		SpringApplication.run(Gosterim360Application.class, args);
	}

}
