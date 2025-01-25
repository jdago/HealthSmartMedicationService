package com.example.medicationservice.domain;
public class InvalidDosageRangeException extends RuntimeException {
    public InvalidDosageRangeException (int dosageInMilligrams) {
        super("The dosage provided: " + dosageInMilligrams + " is not within an acceptable range.");
    }
}
