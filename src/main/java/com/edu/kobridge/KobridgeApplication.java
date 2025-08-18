package com.edu.kobridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KobridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KobridgeApplication.class, args);
	}

}
