package com.teample.matching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class TeampleMatchingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeampleMatchingApplication.class, args);
    }

}
