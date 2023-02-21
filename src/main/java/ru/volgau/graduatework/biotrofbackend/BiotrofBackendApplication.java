package ru.volgau.graduatework.biotrofbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BiotrofBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiotrofBackendApplication.class, args);
	}

}
