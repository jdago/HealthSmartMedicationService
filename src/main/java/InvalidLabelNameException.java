public class InvalidLabelNameException extends RuntimeException {

    public InvalidLabelNameException(String label) {
        super("Invalid formatting of label name. Please try again.");
    }
}
