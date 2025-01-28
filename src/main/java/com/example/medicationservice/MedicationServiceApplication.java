package com.example.medicationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class MedicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicationServiceApplication.class, args);
    }

}
