package ro.redteam.taskmanagementsystem.exceptions;

public class DataExistsException extends RuntimeException {
    public DataExistsException(String message) {
        super(message);
    }
}