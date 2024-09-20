package com.hubzlo.hubzlo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hubzlo.hubzlo.repository")
public class HubZloApplication {

	public static void main(String[] args) {
		SpringApplication.run(HubZloApplication.class, args);
	}

}
