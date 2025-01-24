public class MedicationHistoryDoesNotExistException extends RuntimeException{
    public MedicationHistoryDoesNotExistException (String patientId) {

        super("No existing medical history.");
    }
}
