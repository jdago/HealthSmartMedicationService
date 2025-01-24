public class InvalidPatientIdException extends RuntimeException{

    public InvalidPatientIdException(String patientId) {

        super("Invalid formatting of patient ID: " + patientId + "Example format is XAB-1282");
    }

}
