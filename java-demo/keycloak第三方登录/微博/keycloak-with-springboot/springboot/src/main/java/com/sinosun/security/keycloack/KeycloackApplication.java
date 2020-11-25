package com.sinosun.security.keycloack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sinosun.*")
public class KeycloackApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloackApplication.class, args);
	}

}
