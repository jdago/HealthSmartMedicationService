public class InvalidPatientIdException extends RuntimeException{

    public InvalidPatientIdException(String patientId) {
        super("Invalid formatting of patient ID. Please try again!");
    }

}
