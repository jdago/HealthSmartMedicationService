package com.example.medicationservice.domain;
public class LabelNotFoundException extends RuntimeException {

    public LabelNotFoundException(String medicationName, int dosageInMilligrams) {
        super("The label for " + medicationName + "with dosage in milligrams of " + dosageInMilligrams +
                " does not exist.");
    }
}
