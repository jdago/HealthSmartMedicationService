package com.example.medicationservice;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class MedicationValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenMedicationNameDefinedValidationSucceeds() {
                //assert that the patient Id's is a fixed value
                //assert that the medication name is defined
                //if the medication name exists in the database, then it can be verified
    }

    @Test
    void whenMedicationNameUndefinedValidationFails() {

    }

    @Test
    void whenMedicationNDCDefinedValidationSucceeds() {

    }

    @Test
    void whenMedicationNDCUndefinedValidationFails() {

    }

    @Test
    void whenMedicationNDCFormatDefinedValidationSucceeds() {


    }

    @Test
    void whenMedicationNDCFormatUndefinedValidationSucceeds() {

    }

    @Test
    void whenMedicationDosageDefinedValidationSucceeds() {

    }

    @Test
    void whenMedicationDosageUndefinedValidationSucceeds() {

    }

    @Test
    void whenMedicationDosageIsPositiveGreaterThanZeroValidationSucceeds() {

    }

    @Test
    void whenMedicationDosageIsNotPositiveGreaterThanZeroValidationFails() {

    }

    @Test
    void whenMedicationLabelIsDefinedValidationSucceeds() {

    }

    @Test
    void whenMedicationLabelIsUndefinedValidationFails() {

    }

    @Test
    void whenPatientIdIsDefinedValidationSucceeds(){

    }

    @Test
    void whenPatientIdIsUndefinedValidationFails() {

    }

    @Test
    void whenPatientIdIsFormattedProperlyValidationSucceeds(){

    }

    @Test
    void whenPatientIdIsNotFormattedProperlyValidationFails(){

    }
}