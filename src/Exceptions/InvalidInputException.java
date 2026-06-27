package Exceptions;

/**
 * Custom checked exception used across the system to report
 * invalid user input and business-rule violations (BR29).
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
