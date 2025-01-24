import com.example.medicationservice.Medication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MedicationJsonTests {

    @Autowired
    private JacksonTester<Medication> json;

    @Test
    void testSerialize() throws Exception {
        var medicationTest = new Medication(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");
        var jsonContent = json.write(medicationTest);
        assertThat(jsonContent).extractingJsonPathStringValue("@.medicationName").isEqualTo(medicationTest.medicationName());
        assertThat(jsonContent).extractingJsonPathStringValue("@.medicationNDC").isEqualTo(medicationTest.medicationNDC());
        assertThat(jsonContent).extractingJsonPathStringValue("@.expirationDate").isEqualTo(medicationTest.expirationDate().toString());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.dosageInMilligrams").isEqualTo(medicationTest.dosageInMilligrams());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.availableRefills").isEqualTo(medicationTest.availableRefills());
        assertThat(jsonContent).extractingJsonPathStringValue("@.controlledStatus").isEqualTo(medicationTest.controlledStatus());
        assertThat(jsonContent).extractingJsonPathStringValue("@.medicationClass").isEqualTo(medicationTest.medicationClass());
        assertThat(jsonContent).extractingJsonPathStringValue("@.status").isEqualTo(medicationTest.status());
        assertThat(jsonContent).extractingJsonPathStringValue("@.patientId").isEqualTo(medicationTest.patientId());
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
