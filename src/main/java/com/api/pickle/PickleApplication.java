package com.api.pickle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PickleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickleApplication.class, args);
	}

}
