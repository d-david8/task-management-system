package ro.redteam.taskmanagementsystem.exceptions;

public class InvalidUserIdOrTaskIdException extends RuntimeException {
    public InvalidUserIdOrTaskIdException(String message) {
        super(message);
    }
}
