package com.example.medicationservice;

import com.example.medicationservice.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.context.annotation.Import;

import javax.swing.*;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MedicationServiceApplicationTests {

    @Autowired WebTestClient webTestClient;
    @Test
    void contextLoads() {
    }

    void whenGetMedicationRequestThenMedicationSearched() {
        var expectedMedication = "fioricet";
        var patientId = "508905";

   //     webTestClient
                //.get()
                //.uri("/medications/medications")
                //.bodyValue(expectedMedication)
               // .expectStatus.isSearched()
               // .expectBody(Medication.class).value(actualMedication -> {
                 //   assertThat(actualMedication).isNotNull();
                 //   assertThat(actualMedication.medicationName().isEqualTo(expectedMedication.medicationName()));
             //   };
    }

}
