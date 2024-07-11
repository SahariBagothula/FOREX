package com.forexservice.ForexService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.forexservice.ForexService.Controller.AdminAuthenticationController")

public class ForexServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForexServiceApplication.class, args);
	}

}
