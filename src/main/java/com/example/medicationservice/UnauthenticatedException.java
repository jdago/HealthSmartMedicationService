package com.example.medicationservice;

public class UnauthenticatedException extends RuntimeException{

    public UnauthenticatedException(String userId) {

        super("User is not authenticated to access this resource.");
    }
}
