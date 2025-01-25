package com.example.medicationservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDate;

public record Medication(
        @Version
        int version,
        @NotBlank(message = "The medication name must be defined.")
        String medicationName,
        @NotBlank(message = "The medication's NDC must be defined." )
        @Pattern(regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{2}$", message = "The medication NDC must be defined in the format 0000-0000-00")
        String medicationNDC,

        LocalDate expirationDate,
        @NotBlank(message = "The medication's dosage must be defined.")
        @Positive(message = "The medication's dosage must be greater than a zero")
        int dosageInMilligrams,
        @NotNull(message = "The medication's refills must be defined.")
        @Positive(message = "The medication refills must be greater than zero and must be a whole number.")
        int availableRefills,
        @NotBlank(message = "The medication's controlled status must be defined.")
        String controlledStatus,
        @NotBlank(message = "The medication's class must be defined.")
        String medicationClass,
        @NotBlank(message = "The status of the medication must be defined.")
        String status,
        @NotBlank(message = "The ID of the patient that a medication belongs to must be defined.")
        @Pattern(regexp = "^[A-Z]{3}-[0-9A-Z]{4}$", message = "The patient must be in the format: [A-Z]{3}-[0-9]{4}[A-Z]{2}")
        @Id
        String patientId
        ) {
        public static Medication of(
                int version, String medicationName, String medicationNDC,LocalDate expirationDate, int dosageInMilligrams, int availableRefills, String controlledStatus,
                String medicationClass, String status, String patientId) {
                return new Medication(0, medicationName, medicationNDC, expirationDate, 0, 0, controlledStatus,
                        medicationClass, status, patientId);
        }
}


