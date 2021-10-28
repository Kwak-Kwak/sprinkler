package com.sprinkler.kwakkwak;

import com.sprinkler.kwakkwak.service.RankingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalTime;

@EnableJpaAuditing
@SpringBootApplication
public class KwakkwakApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwakkwakApplication.class, args);


	}

}
