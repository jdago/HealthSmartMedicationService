package com.example.medicationservice;

public class MedicationsNotFoundException extends RuntimeException{
    public MedicationsNotFoundException(String medicationName) {
        super("For your account, no medication named " + medicationName + "were found.");
    }

}

