package ro.redteam.taskmanagementsystem.exceptions;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException(String message) {
        super(message);
    }
}