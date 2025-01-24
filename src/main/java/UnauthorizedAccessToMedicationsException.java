public class UnauthorizedAccessToMedicationsException extends RuntimeException{

    public UnauthorizedAccessToMedicationsException(String userId) {
        super("User is not authorized to access this resource.");
    }

}
