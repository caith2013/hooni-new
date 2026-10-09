package com.hooni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class HooniApplication {

	public static void main(String[] args) {
		SpringApplication.run(HooniApplication.class, args);
	}

}
