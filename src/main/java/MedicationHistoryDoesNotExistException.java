public class MedicationHistoryDoesNotExistException extends RuntimeException{
    public MedicationHistoryDoesNotExistException (String patientId) {

        super("The selected patient: " + patientId + " has no records.");
    }
}
