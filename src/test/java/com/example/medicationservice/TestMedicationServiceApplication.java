package com.example.medicationservice;

import org.springframework.boot.SpringApplication;

public class TestMedicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(MedicationServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
