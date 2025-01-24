public class InvalidNDCException extends RuntimeException {
    public InvalidNDCException (String medicationNDC) {
        super("This formatting of the medication's NDC: " + medicationNDC + " is incorrect.");
    }
}
