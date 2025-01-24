import com.example.medicationservice.MedicationsNotFoundException;

public class MedicationDoesNotExistException extends RuntimeException {
    public MedicationDoesNotExistException(String medicationName) {
        super("Patient not prescribed this medication");
    }
}
