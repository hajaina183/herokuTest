package com.springboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.springboot.app.controller"})
public class HelloWorldThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldThymeleafApplication.class, args);
	}

}
