package com.github.thiagodutra.coopvoteservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableRabbit
@EnableSwagger2
public class CoopVoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoopVoteServiceApplication.class, args);
	}

}