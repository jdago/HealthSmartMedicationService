package com.example.medicationservice.web;

import com.example.medicationservice.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MedicationControllerAdvice{

   @ExceptionHandler(MedicationDoesNotExistException.class)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   String MedicationDoesNotExistHandler(MedicationDoesNotExistException ex) {
      return ex.getMessage();
   }

   @ExceptionHandler(InvalidDosageRangeException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   String InvalidDosageRangeHandler(InvalidDosageRangeException ex) {
      return ex.getMessage();
   }

   @ExceptionHandler(InvalidMedicationNameException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   String InvalidMedicationNameHandler(InvalidMedicationNameException ex) {
      return ex.getMessage();
   }

   @ExceptionHandler(InvalidNDCException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   String InvalidNdcExceptionHandler(InvalidNDCException ex) {
      return ex.getMessage();
   }

   @ExceptionHandler(InvalidPatientIdException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   String InvalidPatientIdHandler(InvalidPatientIdException ex){
      return ex.getMessage();
   }

   @ExceptionHandler(LabelNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   String LabelNotFoundHandler(LabelNotFoundException ex){
      return ex.getMessage();
   }

   @ExceptionHandler(MedicationHistoryDoesNotExistException.class)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   String MedicationHistoryDoesNotExistHandler(MedicationHistoryDoesNotExistException ex){
      return ex.getMessage();
   }

   @ExceptionHandler(MedicationNDCNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   String MedicationNDCNotFoundHandler(MedicationNDCNotFoundException ex){
      return ex.getMessage();
   }

   @ExceptionHandler(MedicationsNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   String MedicationsNotFoundHandler(MedicationsNotFoundException ex){
      return ex.getMessage();
   }

   @ExceptionHandler(PatientNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   String PatientNotFoundHandler(PatientNotFoundException ex){
      return ex.getMessage();
   }


   @ExceptionHandler(MethodArgumentNotValidException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
      var errors = new HashMap<String, String>();
      ex.getBindingResult().getAllErrors().forEach(error -> {
         String fieldName = ((FieldError) error).getField();
         String errorMessage = error.getDefaultMessage();
         errors.put(fieldName, errorMessage);
      });
      return errors;
   }
}