package edu.eci.dosw.tdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "edu.eci.dosw.tdd.persistence.repository")
@EntityScan(basePackages = "edu.eci.dosw.tdd.persistence.dao")
@SpringBootApplication
public class DoswLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoswLibraryApplication.class, args);
	}

}
