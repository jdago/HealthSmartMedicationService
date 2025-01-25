package com.example.medicationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestMedicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(MedicationServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
