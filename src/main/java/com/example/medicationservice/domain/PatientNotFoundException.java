package com.example.medicationservice.domain;
public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String patientId){

        super("The patient with ID: " + patientId + " was not found");
    }

}
