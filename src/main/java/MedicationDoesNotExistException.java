import com.example.medicationservice.MedicationsNotFoundException;

public class MedicationDoesNotExistException extends RuntimeException {
    public MedicationDoesNotExistException(String medicationName) {

        super("Medication with name: " + medicationName + " was not found.");
    }
}
