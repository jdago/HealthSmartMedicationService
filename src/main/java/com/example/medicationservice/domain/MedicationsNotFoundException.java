package com.example.medicationservice.domain;
public class MedicationsNotFoundException extends RuntimeException{
    public MedicationsNotFoundException(String medicationName) {
        super("For your account, no medication named " + medicationName + "were found.");
    }

}

