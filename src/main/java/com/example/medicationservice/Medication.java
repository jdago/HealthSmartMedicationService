package com.example.medicationservice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record Medication(
        @NotBlank(message = "The medication name must be defined.")
        String medicationName,
        @NotBlank(message = "The medication's NDC must be defined." )
        @Pattern(regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{2}$", message = "The medication NDC must be defined in the format 0000-0000-00")
        String medicationNDC,
        LocalDate expirationDate,
        @NotBlank(message = "The medication's dosage must be defined.")
        @Positive(message = "The medication's dosage must be greater than a zero")
        int dosage,
        @NotNull(message = "The medication's refills must be defined.")
        @Positive(message = "The medication refills must be greater than zero and must be a whole number.")
        int availableRefills,
        @NotBlank(message = "The medication's controlled status must be defined.")
        String controlledStatus,
        @NotBlank(message = "The medication's class must be defined.")
        String medicationClass,
        @NotBlank(message = "The status of the medication must be defined.")
        String status,
        @NotBlank(message = "The label of the medication must be defined.")
        String label,
        @NotBlank(message = "The ID of the patient that a medication belongs to must be defined.")
        @Pattern(regexp = "^[A-Z]{3}=[0-9A-Z]{4}$", message = "The patient must be in the format: [A-Z]{3}-[0-9]{4}[A-Z]{2}")
        String patientId
        ) {

}
