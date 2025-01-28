package com.example.medicationservice.web;

import com.example.medicationservice.domain.Medication;

import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;

@JsonTest
class MedicationJsonTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JacksonTester<Medication> json;

    @Test
    void testSerialize() throws Exception {
        var medicationTest = new Medication(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");
        var jsonContent = json.write(medicationTest);
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.medicationName").isEqualTo(medicationTest.medicationName());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.medicationNDC").isEqualTo(medicationTest.medicationNDC());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.expirationDate").isEqualTo(medicationTest.expirationDate().toString());
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("@.dosageInMilligrams").isEqualTo(medicationTest.dosageInMilligrams());
        Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("@.availableRefills").isEqualTo(medicationTest.availableRefills());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.controlledStatus").isEqualTo(medicationTest.controlledStatus());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.medicationClass").isEqualTo(medicationTest.medicationClass());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.status").isEqualTo(medicationTest.status());
        Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.patientId").isEqualTo(medicationTest.patientId());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
           {
            "medicationName": "Aspirin",
            "medicationNDC": "1234-5678-01",
            "expirationDate": "2025-12-31",
            "dosageInMilligrams": 100,
            "availableRefills": 5,
            "controlledStatus": "Non-Controlled",
            "medicationClass": "Painkiller",
            "status": "Active",
            "patientId": "XAB-1001"
            }
               """;

        Medication deserializedMedication = json.parse(content).getObject();

        var expectedMedication = new Medication(0, "Aspirin", "1234-5678-01",
                LocalDate.of(2025,12,31), 100, 5, "Non-Controlled",
               "Painkiller", "Active", "XAB-1001" );

        assertThat(deserializedMedication)
                .usingRecursiveComparison()
                .isEqualTo(expectedMedication);
    }
}
