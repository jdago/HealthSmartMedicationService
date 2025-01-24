public class InvalidMedicationNameException extends RuntimeException{

    public InvalidMedicationNameException (String medicationName)
    {
        super("Invalid formatting of medication name: " + medicationName + " . It cannot be empty!");
    }
}
