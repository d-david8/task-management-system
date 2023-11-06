package ro.redteam.taskmanagementsystem.exceptions;

public class NoTaskFoundException extends RuntimeException {
    public NoTaskFoundException(String message) {
        super(message);
    }
}
