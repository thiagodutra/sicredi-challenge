package com.github.thiagodutra.coopvoteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoopVoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoopVoteServiceApplication.class, args);
	}

}