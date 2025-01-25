package com.example.medicationservice.domain;
public class MedicationNDCNotFoundException extends RuntimeException{
    public MedicationNDCNotFoundException (String medicationNDC) {
        super ("The medication NDC: " + medicationNDC + " was not found");
    }
}
