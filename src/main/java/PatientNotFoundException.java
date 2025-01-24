public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(String patientId){
        super("Selected patient does not exist.");
    }

}
